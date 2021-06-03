package com.finwin.cristal.custmate.home.neftImps.pojo.neft_transfer.model;

public class ModelTransferType {
    String transferType,transferId;

    public ModelTransferType(String transferType, String transferId) {
        this.transferType = transferType;
        this.transferId = transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }
}
