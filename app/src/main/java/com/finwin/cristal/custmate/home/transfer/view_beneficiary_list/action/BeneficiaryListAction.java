package com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.action;

import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryListResponse;

public class BeneficiaryListAction {
    public static final int  DEFAULT=-1;
    public static final int  API_ERROR=1;
    public static final int  BENE_LIST_SUCCESS=2;
    public static final int  CLICK_BACK=3;
    public int action;
    public String error;

    public BeneficiaryListAction(int action) {
        this.action = action;
    }

    public BeneficiaryListAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public BeneficiaryListResponse beneficiaryListResponse;

    public BeneficiaryListAction(int action, BeneficiaryListResponse beneficiaryListResponse) {
        this.action = action;
        this.beneficiaryListResponse = beneficiaryListResponse;
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

    public BeneficiaryListResponse getBeneficiaryListResponse() {
        return beneficiaryListResponse;
    }

    public void setBeneficiaryListResponse(BeneficiaryListResponse beneficiaryListResponse) {
        this.beneficiaryListResponse = beneficiaryListResponse;
    }
}
