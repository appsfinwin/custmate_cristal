package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.adapter;

import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_list.TransactionData;

public class TransactionListRowAction {
    public static final int DEFAULT=-1;
    public static final int ITEM_CLICK=1;

    int action;
    public TransactionData transactionData;

    public TransactionListRowAction(int action, TransactionData transactionData) {
        this.action = action;
        this.transactionData = transactionData;
    }

    public TransactionListRowAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public TransactionData getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(TransactionData transactionData) {
        this.transactionData = transactionData;
    }
}
