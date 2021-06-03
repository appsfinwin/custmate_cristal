package com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.get_account_holder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ACNO")
    @Expose
    private String accountNumber;
    @SerializedName("NAME")
    @Expose
    private String name;
    @SerializedName("CUST_ID")
    @Expose
    private String custId;
    @SerializedName("MOBILE")
    @Expose
    private String mobile;
    @SerializedName("CURRENT_BALANCE")
    @Expose
    private String currentBalance;
    @SerializedName("ACC_STATUS")
    @Expose
    private String accountStatus;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}