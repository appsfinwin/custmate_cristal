package com.finwin.cristal.custmate.home.transfer.search_beneficiary.adapter;

import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryData;

public class SearchBeneficiaryRowAction {

    public static final int CLICK_BENEFICIARY=1;
    public BeneficiaryData beneficiaryData;
    int action;

    public SearchBeneficiaryRowAction(BeneficiaryData beneficiaryData) {
        this.beneficiaryData = beneficiaryData;
    }

    public BeneficiaryData getBeneficiaryData() {
        return beneficiaryData;
    }

    public void setBeneficiaryData(BeneficiaryData beneficiaryData) {
        this.beneficiaryData = beneficiaryData;
    }

    public SearchBeneficiaryRowAction( int action,BeneficiaryData beneficiaryData) {
        this.beneficiaryData = beneficiaryData;
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
