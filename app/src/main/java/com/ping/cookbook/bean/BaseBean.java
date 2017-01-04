package com.ping.cookbook.bean;

import java.util.List;

/**
 * Created by HP on 2016/7/27.
 */
public class BaseBean<T> {
    private int error_code;
    private String reason;
    public List<T> result;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
