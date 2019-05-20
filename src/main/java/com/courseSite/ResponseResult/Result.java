package com.courseSite.ResponseResult;

public class Result<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setOK(String message,T data){
        this.message = message;
        this.code = 200;
        this.data = data;
    }

    public void setFail(int code,String message){
        this.message = message;
        this.code = code;
        this.data = null;
    }

    public void clear(){
        this.message = null;
        this.code = 0;
        this.data = null;
    }
}
