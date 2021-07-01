package com.finwin.cristal.custmate.home.reacharge.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

@SerializedName("msg")
@Expose
private String msg;

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}
    @SerializedName("status")
    @Expose
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}