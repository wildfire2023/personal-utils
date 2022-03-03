package site.itcat.spring.threadfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : xuebengang
 * @date : 2022/3/3
 * @description : 异步线程池配置，在需要使用异步线程的地方通过@Async使用即可
 */
@EnableAsync
@EnableScheduling
@Configuration
public class AsyncThreadPoolConfiguration extends AsyncConfigurerSupport {
    private static final Logger log = LoggerFactory.getLogger(AsyncThreadPoolConfiguration.class);

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Async thread pool manipulate-");
        // 设置核心线程数量
        executor.setCorePoolSize(getCorePoolSize());
        // 设置阻塞队列大小
        executor.setQueueCapacity(1024);
        // 设置最大线程数量
        executor.setMaxPoolSize(20);
        // 设置空闲线程存活时间
        executor.setKeepAliveSeconds(60);
        // 执行拒绝策略：调用主线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    private Integer getCorePoolSize() {
        return Runtime.getRuntime().availableProcessors();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }

    static class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        //手动处理捕获的异常
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            System.out.println("-------------》》》捕获线程异常信息");
            log.info("Exception message - " + throwable.getMessage());
            log.info("Method name - " + method.getName());
            for (Object param : obj) {
                log.info("Parameter value - " + param);
            }
        }

    }
}
