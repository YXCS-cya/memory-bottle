package com.memorybottle.memory_app.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setData(data);
        return r;
    }

    public static <T> Result<T> failure(String message) {
        Result<T> r = new Result<>();
        r.setCode(400);
        r.setMessage(message);
        r.setData(null);
        return r;
    }
}
