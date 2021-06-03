package com.finwin.cristal.custmate.login.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccNo {

@SerializedName("accNo")
@Expose
private String accNo;

public String getAccNo() {
return accNo;
}

public void setAccNo(String accNo) {
this.accNo = accNo;
}
    @SerializedName("schname")
    @Expose
    private String schname;

    @SerializedName("schname_cat")
    @Expose
    private String schemeName;

    public String getSchname() {
        return schname;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public void setSchname(String schname) {
        this.schname = schname;
    }
}