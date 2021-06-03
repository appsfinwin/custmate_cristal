package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_status;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ben {

@SerializedName("data")
@Expose
private List<TransactionStatusData> data = null;

public List<TransactionStatusData> getData() {
return data;
}

public void setData(List<TransactionStatusData> data) {
this.data = data;
}

}