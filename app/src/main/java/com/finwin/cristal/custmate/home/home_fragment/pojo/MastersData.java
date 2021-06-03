package com.finwin.cristal.custmate.home.home_fragment.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MastersData {

@SerializedName("ID")
@Expose
private String iD;
@SerializedName("NAME")
@Expose
private String nAME;
@SerializedName("VALUE")
@Expose
private String vALUE;

public String getID() {
return iD;
}

public void setID(String iD) {
this.iD = iD;
}

public String getNAME() {
return nAME;
}

public void setNAME(String nAME) {
this.nAME = nAME;
}

public String getVALUE() {
return vALUE;
}

public void setVALUE(String vALUE) {
this.vALUE = vALUE;
}

}