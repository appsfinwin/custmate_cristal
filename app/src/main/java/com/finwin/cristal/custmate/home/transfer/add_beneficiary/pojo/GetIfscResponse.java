package com.finwin.cristal.custmate.home.transfer.add_beneficiary.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIfscResponse {

    @SerializedName("CENTRE")
    @Expose
    private String cENTRE;
    @SerializedName("RTGS")
    @Expose
    private Boolean rTGS;
    @SerializedName("IMPS")
    @Expose
    private Boolean iMPS;
    @SerializedName("ADDRESS")
    @Expose
    private String aDDRESS;
    @SerializedName("STD CODE")
    @Expose
    private String sTDCODE;
    @SerializedName("DISTRICT")
    @Expose
    private String dISTRICT;
    @SerializedName("STATE")
    @Expose
    private String sTATE;
    @SerializedName("MICR CODE")
    @Expose
    private String mICRCODE;
    @SerializedName("MICR")
    @Expose
    private String mICR;
    @SerializedName("CONTACT")
    @Expose
    private String cONTACT;
    @SerializedName("CITY")
    @Expose
    private String cITY;
    @SerializedName("NEFT")
    @Expose
    private Boolean nEFT;
    @SerializedName("BRANCH")
    @Expose
    private String bRANCH;
    @SerializedName("UPI")
    @Expose
    private Boolean uPI;
    @SerializedName("BANK")
    @Expose
    private String bANK;
    @SerializedName("BANKCODE")
    @Expose
    private String bANKCODE;
    @SerializedName("IFSC")
    @Expose
    private String iFSC;

    public String getCENTRE() {
        return cENTRE;
    }

    public void setCENTRE(String cENTRE) {
        this.cENTRE = cENTRE;
    }

    public Boolean getRTGS() {
        return rTGS;
    }

    public void setRTGS(Boolean rTGS) {
        this.rTGS = rTGS;
    }

    public Boolean getIMPS() {
        return iMPS;
    }

    public void setIMPS(Boolean iMPS) {
        this.iMPS = iMPS;
    }

    public String getADDRESS() {
        return aDDRESS;
    }

    public void setADDRESS(String aDDRESS) {
        this.aDDRESS = aDDRESS;
    }

    public String getSTDCODE() {
        return sTDCODE;
    }

    public void setSTDCODE(String sTDCODE) {
        this.sTDCODE = sTDCODE;
    }

    public String getDISTRICT() {
        return dISTRICT;
    }

    public void setDISTRICT(String dISTRICT) {
        this.dISTRICT = dISTRICT;
    }

    public String getSTATE() {
        return sTATE;
    }

    public void setSTATE(String sTATE) {
        this.sTATE = sTATE;
    }

    public String getMICRCODE() {
        return mICRCODE;
    }

    public void setMICRCODE(String mICRCODE) {
        this.mICRCODE = mICRCODE;
    }

    public String getMICR() {
        return mICR;
    }

    public void setMICR(String mICR) {
        this.mICR = mICR;
    }

    public String getCONTACT() {
        return cONTACT;
    }

    public void setCONTACT(String cONTACT) {
        this.cONTACT = cONTACT;
    }

    public String getCITY() {
        return cITY;
    }

    public void setCITY(String cITY) {
        this.cITY = cITY;
    }

    public Boolean getNEFT() {
        return nEFT;
    }

    public void setNEFT(Boolean nEFT) {
        this.nEFT = nEFT;
    }

    public String getBRANCH() {
        return bRANCH;
    }

    public void setBRANCH(String bRANCH) {
        this.bRANCH = bRANCH;
    }

    public Boolean getUPI() {
        return uPI;
    }

    public void setUPI(Boolean uPI) {
        this.uPI = uPI;
    }

    public String getBANK() {
        return bANK;
    }

    public void setBANK(String bANK) {
        this.bANK = bANK;
    }

    public String getBANKCODE() {
        return bANKCODE;
    }

    public void setBANKCODE(String bANKCODE) {
        this.bANKCODE = bANKCODE;
    }

    public String getIFSC() {
        return iFSC;
    }

    public void setIFSC(String iFSC) {
        this.iFSC = iFSC;
    }


    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}