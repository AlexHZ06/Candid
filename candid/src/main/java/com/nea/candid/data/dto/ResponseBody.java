package com.nea.candid.data.dto;

public class ResponseBody<T> {

    private T data;
    private String error;
    private int internalCode;
    private boolean success;

    private ResponseBody() {

    }

    public static ResponseBody<String> error(String message, int internalCode) {
        ResponseBody<String> responseBody = new ResponseBody<>();
        responseBody.setError(message);
        responseBody.setInternalCode(internalCode);
        responseBody.setSuccess(false);
        return responseBody;
    }

    public static <T> ResponseBody<T> success(T data, int internalCode) {
        ResponseBody<T> responseBody = new ResponseBody<>();
        responseBody.setSuccess(true);
        responseBody.setInternalCode(internalCode);
        responseBody.setData(data);
        return responseBody;
    }

    public Object getData() {
        return data;
    }

    public int getInternalCode() {
        return internalCode;
    }

    public boolean isSucsess() {
        return success;
    }

    private void setError(String error) {
        this.error = error;
    }

    private void setInternalCode(int internalCode) {
        this.internalCode = internalCode;
    }

    private void setSuccess(boolean sucsess) {
        this.success = sucsess;
    }

    private void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }
}
