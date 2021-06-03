package com.finwin.cristal.custmate.home.reacharge.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RechargeResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("msg")
@Expose
private String msg;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

}