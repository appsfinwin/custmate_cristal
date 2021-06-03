package com.finwin.cristal.custmate.home.reacharge.pojo.get_circle_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CircleData {

@SerializedName("cir_id")
@Expose
private String cirId;
@SerializedName("cir_name")
@Expose
private String cirName;
@SerializedName("id")
@Expose
private String id;

public String getCirId() {
return cirId;
}

public void setCirId(String cirId) {
this.cirId = cirId;
}

public String getCirName() {
return cirName;
}

public void setCirName(String cirName) {
this.cirName = cirName;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}
    public CircleData(String cirId, String cirName, String id) {
        this.cirId = cirId;
        this.cirName = cirName;
        this.id = id;
    }

}