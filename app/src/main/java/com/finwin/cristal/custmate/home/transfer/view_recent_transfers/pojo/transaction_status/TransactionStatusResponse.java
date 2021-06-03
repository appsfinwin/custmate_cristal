package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_status;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionStatusResponse {

@SerializedName("ben")
@Expose
private Ben ben;

public Ben getBen() {
return ben;
}

public void setBen(Ben ben) {
this.ben = ben;
}

}