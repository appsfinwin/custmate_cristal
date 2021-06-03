package com.finwin.cristal.custmate.home.mini_statement.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TRANSACTON {

@SerializedName("TXN_ID")
@Expose
private String tXNID;
@SerializedName("TXN_DATE")
@Expose
private String tXNDATE;
@SerializedName("PARTICULAR")
@Expose
private String pARTICULAR;
@SerializedName("DORC")
@Expose
private String dORC;
@SerializedName("TRAN_TYPE")
@Expose
private String tRANTYPE;
@SerializedName("TXN_AMOUNT")
@Expose
private String tXNAMOUNT;
@SerializedName("BALANCE")
@Expose
private String bALANCE;

public String getTXNID() {
return tXNID;
}

public void setTXNID(String tXNID) {
this.tXNID = tXNID;
}

public String getTXNDATE() {
return tXNDATE;
}

public void setTXNDATE(String tXNDATE) {
this.tXNDATE = tXNDATE;
}

public String getPARTICULAR() {
return pARTICULAR;
}

public void setPARTICULAR(String pARTICULAR) {
this.pARTICULAR = pARTICULAR;
}

public String getDORC() {
return dORC;
}

public void setDORC(String dORC) {
this.dORC = dORC;
}

public String getTRANTYPE() {
return tRANTYPE;
}

public void setTRANTYPE(String tRANTYPE) {
this.tRANTYPE = tRANTYPE;
}

public String getTXNAMOUNT() {
return tXNAMOUNT;
}

public void setTXNAMOUNT(String tXNAMOUNT) {
this.tXNAMOUNT = tXNAMOUNT;
}

public String getBALANCE() {
return bALANCE;
}

public void setBALANCE(String bALANCE) {
this.bALANCE = bALANCE;
}

}