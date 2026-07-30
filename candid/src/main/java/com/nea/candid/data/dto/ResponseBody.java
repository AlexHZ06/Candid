package com.nea.candid.data.dto;

public class ResponseBody {

    private Object data;
    private int internalCode;
    private boolean sucsess;

    public ResponseBody(String message, int internalCode, boolean sucsess) {
        this.data = message;
        this.internalCode = internalCode;
        this.sucsess = sucsess;
    }

    public Object getData() {
        return data;
    }

    public int getInternalCode() {
        return internalCode;
    }

    public boolean isSucsess() {
        return sucsess;
    }
}
