package com.yjy.test.game.util.pojo;

/**
 * return 工具实体
 *
 * @author yjy
 */
public class ReturnEntity {

    private boolean result;//返回正确 或 错误
    private String msg; //返回附带信息
    private Object returnObj; //返回附带对象


    public ReturnEntity() {
    }

    public ReturnEntity(boolean result, String msg, Object obj) {
        this.setResult(result);
        this.setMsg(msg);
        this.setReturnObj(obj);
    }

    public ReturnEntity(boolean result, String msg) {
        this.setResult(result);
        this.setMsg(msg);
    }

    public ReturnEntity(boolean result, Object obj) {
        this.setResult(result);
        this.setReturnObj(obj);
    }

    public ReturnEntity(boolean result) {
        this.setResult(result);
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getReturnObj() {
        return returnObj;
    }

    public void setReturnObj(Object returnObj) {
        this.returnObj = returnObj;
    }

}
