package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.transaction_details;

import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_status.TransactionStatusResponse;

public class TransactionDetailsAction {
    public static final int DEFAULT=-1;
    public static final int API_ERROR=1;
    public static final int TRANSACTION_DETAILS=2;
    public static final int BACK_PRESSED=3;
    TransactionStatusResponse transactionStatusResponse;

    int action;
    String error;

    public TransactionDetailsAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public TransactionDetailsAction(int action) {
        this.action = action;
    }

    public TransactionDetailsAction(int action,TransactionStatusResponse transactionStatusResponse) {
        this.transactionStatusResponse = transactionStatusResponse;
        this.action = action;
    }

    public TransactionStatusResponse getTransactionStatusResponse() {
        return transactionStatusResponse;
    }

    public void setTransactionStatusResponse(TransactionStatusResponse transactionStatusResponse) {
        this.transactionStatusResponse = transactionStatusResponse;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
