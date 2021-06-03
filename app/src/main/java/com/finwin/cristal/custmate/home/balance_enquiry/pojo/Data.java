package com.finwin.cristal.custmate.home.balance_enquiry.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("DATE")
@Expose
private String dATE;
@SerializedName("ACC_NO")
@Expose
private String aCCNO;
@SerializedName("NAME")
@Expose
private String nAME;
@SerializedName("MOBILE")
@Expose
private String mOBILE;
@SerializedName("CURRENT_BALANCE")
@Expose
private String cURRENTBALANCE;

public String getDATE() {
return dATE;
}

public void setDATE(String dATE) {
this.dATE = dATE;
}

public String getACCNO() {
return aCCNO;
}

public void setACCNO(String aCCNO) {
this.aCCNO = aCCNO;
}

public String getNAME() {
return nAME;
}

public void setNAME(String nAME) {
this.nAME = nAME;
}

public String getMOBILE() {
return mOBILE;
}

public void setMOBILE(String mOBILE) {
this.mOBILE = mOBILE;
}

public String getCURRENTBALANCE() {
return cURRENTBALANCE;
}

public void setCURRENTBALANCE(String cURRENTBALANCE) {
this.cURRENTBALANCE = cURRENTBALANCE;
}

}