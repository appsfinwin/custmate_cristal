package com.finwin.cristal.custmate.home.neftImps.pojo.neft_transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("TRAN_ID")
@Expose
private String tranId;
@SerializedName("ACC_NO")
@Expose
private String accNo;
@SerializedName("BEN_ACC_NO")
@Expose
private String benAccNo;
@SerializedName("NAME")
@Expose
private String name;
@SerializedName("MOBILE")
@Expose
private String mobile;
@SerializedName("OLD_BALANCE")
@Expose
private String oldBalance;
@SerializedName("WITHDRAWAL_AMOUNT")
@Expose
private String withdrawalAmount;
@SerializedName("CURRENT_BALANCE")
@Expose
private String currentBalance;
@SerializedName("TRANSFER_DATE")
@Expose
private String transferDate;


    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBenAccNo() {
        return benAccNo;
    }

    public void setBenAccNo(String benAccNo) {
        this.benAccNo = benAccNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(String oldBalance) {
        this.oldBalance = oldBalance;
    }

    public String getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(String withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }
}