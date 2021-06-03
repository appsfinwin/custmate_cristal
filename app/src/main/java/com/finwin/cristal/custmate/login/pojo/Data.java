package com.finwin.cristal.custmate.login.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("ID")
@Expose
private String iD;
@SerializedName("USER_ID")
@Expose
private String uSERID;
@SerializedName("USER_NAME")
@Expose
private String uSERNAME;
@SerializedName("BRANCH_ID")
@Expose
private String bRANCHID;
@SerializedName("MOBILE_NO")
@Expose
private String mOBILENO;
@SerializedName("accNo")
@Expose
private List<AccNo> accNo = null;
@SerializedName("MPINstatus")
@Expose
private Boolean mPINstatus;

public String getID() {
return iD;
}

public void setID(String iD) {
this.iD = iD;
}

public String getUSERID() {
return uSERID;
}

public void setUSERID(String uSERID) {
this.uSERID = uSERID;
}

public String getUSERNAME() {
return uSERNAME;
}

public void setUSERNAME(String uSERNAME) {
this.uSERNAME = uSERNAME;
}

public String getBRANCHID() {
return bRANCHID;
}

public void setBRANCHID(String bRANCHID) {
this.bRANCHID = bRANCHID;
}

public String getMOBILENO() {
return mOBILENO;
}

public void setMOBILENO(String mOBILENO) {
this.mOBILENO = mOBILENO;
}

public List<AccNo> getAccNo() {
return accNo;
}

public void setAccNo(List<AccNo> accNo) {
this.accNo = accNo;
}

public Boolean getMPINstatus() {
return mPINstatus;
}

public void setMPINstatus(Boolean mPINstatus) {
this.mPINstatus = mPINstatus;
}

}