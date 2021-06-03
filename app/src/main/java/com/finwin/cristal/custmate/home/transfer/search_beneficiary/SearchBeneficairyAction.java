package com.finwin.cristal.custmate.home.transfer.search_beneficiary;

import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryListResponse;

public class SearchBeneficairyAction {

    public static final int DEFAULT=-1;
    public static final int API_ERROR=1;
    public static final int BENEFICIARY_SUCCESS=2;
    public int action;
    public String error;
    public BeneficiaryListResponse beneficiaryListResponse;
    public SearchBeneficairyAction(int action) {
        this.action = action;
    }

    public SearchBeneficairyAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public SearchBeneficairyAction(int action, BeneficiaryListResponse beneficiaryListResponse) {
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

    public static int getDEFAULT() {
        return DEFAULT;
    }

    public BeneficiaryListResponse getBeneficiaryListResponse() {
        return beneficiaryListResponse;
    }

    public void setBeneficiaryListResponse(BeneficiaryListResponse beneficiaryListResponse) {
        this.beneficiaryListResponse = beneficiaryListResponse;
    }
}
