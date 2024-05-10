package com.example.appbanhangonlinereal.model;

import java.util.List;

public class AdminFunctionModel {
    boolean success;
    String message;
    List<AdminFunction> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AdminFunction> getResult() {
        return result;
    }

    public void setResult(List<AdminFunction> result) {
        this.result = result;
    }
}
