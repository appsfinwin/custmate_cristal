package com.finwin.cristal.custmate.home.reacharge.pojo.get_operator_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOperatorResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("data")
@Expose
private List<OperatorData> data = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public List<OperatorData> getData() {
return data;
}

public void setData(List<OperatorData> data) {
this.data = data;
}


    @SerializedName("msg")
    @Expose
    private String msg;



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}