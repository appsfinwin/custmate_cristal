package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.adapter;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_list.TransactionData;

public class TransactionListRowViewmodel extends BaseObservable {
    TransactionData transactionData;
    MutableLiveData<TransactionListRowAction> mAction;
    String name,date,transactionId,amount,debitIndicator,beneficaiaryName,transferType,statusDescription;

    public TransactionListRowViewmodel(TransactionData transactionData, MutableLiveData<TransactionListRowAction> mAction) {
        this.transactionData = transactionData;
        this.mAction = mAction;
    }

    public void setData(TransactionData data) {
        this.transactionData=data;
    }

    public String getName() {
        name=transactionData.getNAME();
        return name;
    }

    public String getDate() {
        date=transactionData.getPaymentRunDate();
        return date;
    }

    public String getTransactionId() {
        transactionId=transactionData.getTRANIDDIGICOB();
        return transactionId;
    }

    public String getAmount() {
        amount="₹ "+transactionData.getAmount();
        return amount;
    }

    public String getDebitIndicator() {
        debitIndicator=transactionData.getDebitCreditIndicator();
        return debitIndicator;
    }

//    public String getBeneficaiaryName() {
//        beneficaiaryName=transactionData.bene();
//        return beneficaiaryName;
//    }

    public String getTransferType() {
        return transferType;
    }

    public String getStatusDescription() {
        statusDescription=transactionData.getStatusCode()+" ("+transactionData.getStatusDescription()+")";
        return statusDescription;
    }

    public void itemClick(View view)
    {
        mAction.setValue(new TransactionListRowAction(TransactionListRowAction.ITEM_CLICK,transactionData));
    }
}
