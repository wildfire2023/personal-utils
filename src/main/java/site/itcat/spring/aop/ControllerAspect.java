package site.itcat.spring.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : xuebengang
 * @date : 2021/10/19
 * @description : 统一日志
 */
@Component
@Aspect
public class ControllerAspect {


    @Pointcut("execution(* com.vantai.dataplatform.pay.web.controller..*.*(..))")
    public void executeController() {
    }

    @Around("executeController()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object result = proceedingJoinPoint.proceed();
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setIp(request.getRemoteAddr());
        requestInfo.setUrl(request.getRequestURL().toString());
        requestInfo.setHttpMethod(request.getMethod());
        requestInfo.setClassMethod(String.format("%s.%s", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName()));
        requestInfo.setRequestParams(getRequestParamsByProceedingJoinPoint(proceedingJoinPoint));
        requestInfo.setResult(result);
        requestInfo.setTimeCost(System.currentTimeMillis() - start);
//        log.info("Request Info      : {}", JSON.toJSONString(requestInfo));
        return result;
    }


    @AfterThrowing(value = "executeController()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, RuntimeException e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
        requestErrorInfo.setIp(request.getRemoteAddr());
        requestErrorInfo.setUrl(request.getRequestURL().toString());
        requestErrorInfo.setHttpMethod(request.getMethod());
        requestErrorInfo.setClassMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()));
        requestErrorInfo.setRequestParams(getRequestParamsByJoinPoint(joinPoint));
        requestErrorInfo.setExceptionMsg(e.getMessage());
//        log.info("Error Request Info      : {}", JSON.toJSONString(requestErrorInfo));
    }

    private Object getRequestParamsByJoinPoint(JoinPoint joinPoint) {
        // 参数名称
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] parameterValues = joinPoint.getArgs();
        return buildRequestParam(parameterNames, parameterValues);
    }

    private Map<String, Object> getRequestParamsByProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        // 参数名称
        String[] parameterNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] parameterValues = proceedingJoinPoint.getArgs();
        return buildRequestParam(parameterNames, parameterValues);
    }

    private Map<String, Object> buildRequestParam(String[] parameterNames, Object[] parameterValues) {
        Map<String, Object> requestParams = new java.util.HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            Object value = parameterValues[i];

            if (value instanceof List) {
                try {
                    List<MultipartFile> multipartFiles = castList(value, MultipartFile.class);
                    if (multipartFiles != null) {
                        List<String> fileNames = new ArrayList<>();
                        for (MultipartFile multipartFile : multipartFiles) {
                            fileNames.add(multipartFile.getOriginalFilename());
                        }

                        requestParams.put(parameterNames[i], fileNames);
                        break;
                    }
                } catch (ClassCastException ex) {
                    // 忽略不是文件类型的list
                }
            }
            requestParams.put(parameterNames[i], value);
        }


        return requestParams;
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                list.add(clazz.cast(o));
            }
        }
        return list;
    }


    /**
     * 请求包装
     */
    public class RequestInfo {
        private String ip;
        private String url;
        private String httpMethod;
        private String classMethod;
        private Object requestParams;
        private Object result;
        private Long timeCost;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }

        public String getClassMethod() {
            return classMethod;
        }

        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }

        public Object getRequestParams() {
            return requestParams;
        }

        public void setRequestParams(Object requestParams) {
            this.requestParams = requestParams;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public Long getTimeCost() {
            return timeCost;
        }

        public void setTimeCost(Long timeCost) {
            this.timeCost = timeCost;
        }
    }

    /**
     * 请求错误包装：无返回结果与时间消耗，新增错误信息
     */
    public class RequestErrorInfo {
        private String ip;
        private String url;
        private String httpMethod;
        private String classMethod;
        private Object requestParams;
        private String exceptionMsg;


        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }

        public String getClassMethod() {
            return classMethod;
        }

        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }

        public Object getRequestParams() {
            return requestParams;
        }

        public void setRequestParams(Object requestParams) {
            this.requestParams = requestParams;
        }

        public String getExceptionMsg() {
            return exceptionMsg;
        }

        public void setExceptionMsg(String exceptionMsg) {
            this.exceptionMsg = exceptionMsg;
        }
    }


}
