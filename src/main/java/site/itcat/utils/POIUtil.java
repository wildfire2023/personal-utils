package site.itcat.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class POIUtil {
    private static final Logger log = LoggerFactory.getLogger(POIUtil.class);
    private static final String EXCEL2003L = ".xls";
    private static final String EXCEL2007U = ".xlsx";
    private static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|union|and|or|delete|insert|truncate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
    private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

    /**
     * @param filePath      上传文件路径
     * @param tableName     数据库表名称
     * @param tableNameList 数据库表字段名，顺序与Excel表格列一致
     */
    public static String getImportSQLFromExcel(String filePath, String tableName, List<String> tableNameList) {
        FileInputStream fis = null;
        Workbook workbook = null;
        Sheet sheet = null;
        String sql = null;
        try {
            fis = new FileInputStream(filePath);
            // 获取excel 工作簿
            workbook = getWorkbook(fis, filePath);
            // 获取表
            sheet = workbook.getSheetAt(0);
            // 获取表头行
            Row rowHead = sheet.getRow(0);
            // 获取表列
            int columns = rowHead.getPhysicalNumberOfCells();
            // 获取表行
            int rows = sheet.getLastRowNum();
            sql = setDataInstance(sheet, rows, columns, tableName, tableNameList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return sql;
    }


    private static String setDataInstance(Sheet sheet, int rows, int columns, String tableName, List<String> columnNames) {
        List<List<String>> data = new ArrayList<>();
        for (int i = 1; i <= rows; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                data.add(dataObj(columns, row));
            }
        }
        String sql = null;
        try {
            sql = insertSqlBatch(tableName, columnNames, data);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return sql;
    }


    /**
     * @param tableName
     * @param columnNames
     * @param rowDatas
     * @return 返回批量插入SQL
     * @throws SQLException
     */
    private static String insertSqlBatch(String tableName, List<String> columnNames, List<List<String>> rowDatas) throws SQLException {
        int rows = rowDatas.size();
        boolean rowError = false;
        List<Integer> errors = new ArrayList<>();
        StringBuilder sb = new StringBuilder("insert into " + tableName);
        sb.append("(");
        for (int i = 0; i < columnNames.size(); i++) {
            sb.append(columnNames.get(i));
            if (i != columnNames.size() - 1) {
                sb.append(",");
            } else {
                sb.append(")");
            }
        }
        sb.append(" values ");
        for (int j = 0; j < rows; j++) {
            sb.append("(");
            for (int k = 0; k < rowDatas.get(j).size(); k++) {
                if (sqlPattern.matcher(rowDatas.get(j).get(k)).find()) {
                    rowError = true;
                }
                sb.append("'").append(rowDatas.get(j).get(k)).append("'");
                if (k != rowDatas.get(j).size() - 1) {
                    sb.append(",");
                }
            }
            sb.append(")");
            if (j != rows - 1) {
                sb.append(",");
            }
            // 当前行有SQL注入，取消插入
            if (rowError) {
                // 将有SQL注入行添加至List进行日志输出
                errors.add(j + 2);
                int start = sb.lastIndexOf("(");
                if (j != rows - 1) {
                    int end = sb.lastIndexOf(",");
                    sb.delete(start, end + 1);
                }else {
                    int end = sb.lastIndexOf(")");
                    sb.delete(start, end + 1);
                }
            }
            // 将rowError置为false
            rowError = false;
        }
        if (errors.size() != 0) {
            log.warn("上传的表格内容有SQL注入风险，表格中有如下行取消插入：");
        }
        for (int i = 0; i < errors.size(); i++) {
            log.warn(String.valueOf(errors.get(i)) + ",  ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * 单条记录读取
     *
     * @param columns
     * @param row
     * @return
     */
    public static List<String> dataObj(Integer columns, Row row) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            data.add(getVal(row.getCell(i)));
        }
        return data;
    }

    /**
     * 对单元格格式进行判断
     *
     * @param cell
     * @return
     */
    private static String getVal(Cell cell) {
        String value = null;
        // 格式化字符类型数字
        DecimalFormat df = new DecimalFormat("0");
        // 日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        // 格式化数字
        DecimalFormat df2 = new DecimalFormat("0");
        switch (cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case FORMULA:
                value = df.format(cell.getNumericCellValue());
                break;
            case BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 获取工作簿实例
     *
     * @param inStr
     * @param fileName
     * @return
     * @throws Exception
     */
    private static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        if (fileName.endsWith(EXCEL2003L)) {
            wb = new HSSFWorkbook(inStr);
        } else if (fileName.endsWith(EXCEL2007U)) {
            wb = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("请传入.xls或者.xlsx后缀表格");
        }
        return wb;
    }

    /**
     * List -> row
     * Map -> column
     *
     * @param args
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        getImportSQLFromExcel("G:\\工作\\test1.xlsx", "test1",
                Arrays.asList("name1", "name2", "name3", "name4", "name5", "name6", "name7", "name8",
                        "name9", "name10", "name11", "name12", "name13", "name14", "name15", "name16", "name17"));
        long end = System.currentTimeMillis();
        System.out.println("总消耗：" + (end - start) + " 毫秒");
    }
}
