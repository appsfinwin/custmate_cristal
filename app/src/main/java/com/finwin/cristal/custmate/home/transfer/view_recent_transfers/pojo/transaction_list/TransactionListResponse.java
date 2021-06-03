package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionListResponse {

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