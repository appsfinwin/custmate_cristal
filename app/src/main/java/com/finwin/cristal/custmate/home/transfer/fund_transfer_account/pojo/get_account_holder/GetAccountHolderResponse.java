package com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.get_account_holder;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAccountHolderResponse {

@SerializedName("account")
@Expose
private Account account;

public Account getAccount() {
return account;
}

public void setAccount(Account account) {
this.account = account;
}

}