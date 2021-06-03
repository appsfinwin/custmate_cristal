package com.finwin.cristal.custmate.home.reacharge.pojo.get_circle_response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCircleResponse {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("data")
@Expose
private List<CircleData> data = null;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public List<CircleData> getData() {
return data;
}

public void setData(List<CircleData> data) {
this.data = data;
}

}