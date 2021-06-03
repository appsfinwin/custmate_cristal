package com.finwin.cristal.custmate.home.mini_statement.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("ACC_NO")
@Expose
private String aCCNO;
@SerializedName("NAME")
@Expose
private String nAME;
@SerializedName("MOBILE")
@Expose
private String mOBILE;
@SerializedName("TRANSACTONS")
@Expose
private List<TRANSACTON> tRANSACTONS = null;
@SerializedName("CURRENT_BALANCE")
@Expose
private String cURRENTBALANCE;

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

public List<TRANSACTON> getTRANSACTONS() {
return tRANSACTONS;
}

public void setTRANSACTONS(List<TRANSACTON> tRANSACTONS) {
this.tRANSACTONS = tRANSACTONS;
}

public String getCURRENTBALANCE() {
return cURRENTBALANCE;
}

public void setCURRENTBALANCE(String cURRENTBALANCE) {
this.cURRENTBALANCE = cURRENTBALANCE;
}

}