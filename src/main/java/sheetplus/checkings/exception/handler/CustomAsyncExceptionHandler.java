package sheetplus.checkings.exception.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    /**
     * 비동기에 대해 더 학습한 후, 예외처리에 대해 고민할 계획
     */
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("비동기 처리 예외 발생: {}", ex.getMessage());
    }
}
