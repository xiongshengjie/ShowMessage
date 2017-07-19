package cn.xiong.showmessage.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */

public class BaseEntity<T> {

    private String errCode;
    private String message;
    private List<T> data;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
