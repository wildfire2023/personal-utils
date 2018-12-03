package com.example.jdk.version8.instance;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.function.Predicate;

/**
 * <p>
 *     ThreadLocal:本地线程缓冲
 *     jdk8加入了Supplier的支持，构造ThreadLocal的方式更简洁
 * </p>
 * @author Monster
 * @since  1.8
 * @date 2018/11/25
 */
public class ThreadLocalTest {
    /**
     * DateFormatter非线程安全
     */
    public static final ThreadLocal<DateFormatter> THREAD_LOCAL = ThreadLocal.withInitial(DateFormatter::new);

    public static void main(String[] args) {
        DateFormatter dateFormatter = THREAD_LOCAL.get();
        dateFormatter.setFormat(new SimpleDateFormat("dd-mm-yyyy"));

    }
}
