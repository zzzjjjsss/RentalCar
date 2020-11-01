package com.prudential.car.controller.bean;

public class ApiResponse {
    private ResultCode resultCode=ResultCode.OK;
    private String mes = "";

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public static enum ResultCode {
        OK, FAIL, NO_LOGIN,NO_PERMISSION,LICENSE_EXPIRED
    }
    public static ApiResponse getSucessResponse() {
        ApiResponse response = new ApiResponse();
        response.setResultCode(ResultCode.OK);
        return response;
    }
}
