package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_list;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ben {

@SerializedName("data")
@Expose
private List<TransactionData> data = null;

public List<TransactionData> getData() {
return data;
}

public void setData(List<TransactionData> data) {
this.data = data;
}

}