package com.finwin.cristal.custmate.home.reacharge.pojo.get_operator_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorData {

@SerializedName("opr_id")
@Expose
private String oprId;
@SerializedName("opr_name")
@Expose
private String oprName;
@SerializedName("type")
@Expose
private String type;
@SerializedName("id")
@Expose
private String id;

public String getOprId() {
return oprId;
}

public void setOprId(String oprId) {
this.oprId = oprId;
}

public String getOprName() {
return oprName;
}

public void setOprName(String oprName) {
this.oprName = oprName;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

    public OperatorData(String oprId, String oprName, String type, String id) {
        this.oprId = oprId;
        this.oprName = oprName;
        this.type = type;
        this.id = id;
    }
}