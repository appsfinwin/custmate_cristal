package com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.adapter;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryData;

public class BeneficiaryRowViewmodel extends BaseObservable {
    MutableLiveData<BeneficiaryRowAction> mAction;
    BeneficiaryData data;
    String name,accountNumber;

    public BeneficiaryRowViewmodel(MutableLiveData<BeneficiaryRowAction> mAction, BeneficiaryData data) {
        this.mAction = mAction;
        this.data = data;
    }

    public void setData(BeneficiaryData data) {
        this.data = data;
    }

    public String getName() {
        name=data.getReceiverName();
        return name;
    }

    public String getAccountNumber() {
        accountNumber=data.getReceiverAccountno();
        return accountNumber;
    }

    public void clickDelete(View view)
    {
        mAction.setValue(new BeneficiaryRowAction(BeneficiaryRowAction.CLICK_DELETE,data));
    }
}
