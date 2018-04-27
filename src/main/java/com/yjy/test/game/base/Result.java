package com.yjy.test.game.base;

import com.yjy.test.game.web.ErrorCode;

/**
 * 用户方法返回的工具实体类
 * Created by yjy on 2017/4/28.
 */
public class Result {

    private boolean success; //结果 成功/失败
    private String info; //错误描述
    private ErrorCode code; //错误码
    private Object data; //返回数据

    public Result(boolean success, String info, ErrorCode code, Object data) {
        this.success = success;
        this.info = info;
        this.code = code;
        this.data = data;
    }

    public Result(boolean success, String info, ErrorCode code) {
        this.success = success;
        this.info = info;
        this.code = code;
    }

    public Result(boolean success, ErrorCode code) {
        this.success = success;
        this.code = code;
    }

    public Result(Object data) {
        this.success = true;
        this.data = data;
    }

    public Result(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getData() {
        return data;
    }


    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
