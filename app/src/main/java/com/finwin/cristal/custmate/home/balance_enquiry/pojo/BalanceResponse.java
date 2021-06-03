package com.finwin.cristal.custmate.home.balance_enquiry.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceResponse {

@SerializedName("balance")
@Expose
private Balance balance;

public Balance getBalance() {
return balance;
}

public void setBalance(Balance balance) {
this.balance = balance;
}

}