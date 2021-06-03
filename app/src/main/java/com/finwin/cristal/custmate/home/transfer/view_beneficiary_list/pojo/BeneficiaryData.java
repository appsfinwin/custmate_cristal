package com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeneficiaryData {

@SerializedName("receiverid")
@Expose
private String receiverid;
@SerializedName("receiver_name")
@Expose
private String receiverName;
@SerializedName("receiver_accountno")
@Expose
private String receiverAccountno;
@SerializedName("receiver_ifsccode")
@Expose
private String receiverIfsccode;

public String getReceiverid() {
return receiverid;
}

public void setReceiverid(String receiverid) {
this.receiverid = receiverid;
}

public String getReceiverName() {
return receiverName;
}

public void setReceiverName(String receiverName) {
this.receiverName = receiverName;
}

public String getReceiverAccountno() {
return receiverAccountno;
}

public void setReceiverAccountno(String receiverAccountno) {
this.receiverAccountno = receiverAccountno;
}

public String getReceiverIfsccode() {
return receiverIfsccode;
}

public void setReceiverIfsccode(String receiverIfsccode) {
this.receiverIfsccode = receiverIfsccode;
}

    public BeneficiaryData(String receiverid, String receiverName, String receiverAccountno, String receiverIfsccode) {
        this.receiverid = receiverid;
        this.receiverName = receiverName;
        this.receiverAccountno = receiverAccountno;
        this.receiverIfsccode = receiverIfsccode;
    }
}