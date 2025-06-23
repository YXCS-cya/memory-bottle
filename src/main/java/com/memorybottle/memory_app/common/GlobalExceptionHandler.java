package com.memorybottle.memory_app.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e) {
        return Result.failure("操作失败：" + e.getMessage());
    }

    // 可以继续扩展其他异常处理，如参数异常、数据库异常等，待定吧
}
