package com.finwin.cristal.custmate.home.transfer.search_beneficiary.adapter;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryData;

public class SearchBeneficiaryRowViewmodel extends BaseObservable {

        MutableLiveData<SearchBeneficiaryRowAction> mAction;
        BeneficiaryData data;
        String name,accountNumber;

        public SearchBeneficiaryRowViewmodel(MutableLiveData<SearchBeneficiaryRowAction> mAction, BeneficiaryData data) {
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
            mAction.setValue(new SearchBeneficiaryRowAction(SearchBeneficiaryRowAction.CLICK_BENEFICIARY,data));
        }


}
