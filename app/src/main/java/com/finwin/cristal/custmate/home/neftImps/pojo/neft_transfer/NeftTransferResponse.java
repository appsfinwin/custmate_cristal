package com.finwin.cristal.custmate.home.neftImps.pojo.neft_transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NeftTransferResponse {

@SerializedName("receipt")
@Expose
private Receipt receipt;

public Receipt getReceipt() {
return receipt;
}

public void setReceipt(Receipt receipt) {
this.receipt = receipt;
}

}