package com.finwin.cristal.custmate.home.transfer_main.action;

import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_list.TransactionListResponse;

public class TransferAction {

    public static final int DEFAULT=-1;
    public static final int CLICK_BACK=1;
    public static final int ACCOUNT_TRANSFER=2;
    public static final int NEFT_IMPS=3;
    public static final int ADD_BENEFICIARY=4;
    public static final int VIEW_RECENT_TRANSFERS=5;
    public static final int TRANSACTIONLIST_SUCCESS=6;

    public int action;
    public String error;

    public TransactionListResponse transactionListResponse;

    public TransactionListResponse getTransactionListResponse() {
        return transactionListResponse;
    }

    public void setTransactionListResponse(TransactionListResponse transactionListResponse) {
        this.transactionListResponse = transactionListResponse;
    }

    public TransferAction(int action, TransactionListResponse transactionListResponse) {
        this.action = action;
        this.transactionListResponse = transactionListResponse;
    }

    public TransferAction(int action) {
        this.action = action;
    }

    public TransferAction(int action, String error) {
        this.action = action;
        this.error = error;
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
