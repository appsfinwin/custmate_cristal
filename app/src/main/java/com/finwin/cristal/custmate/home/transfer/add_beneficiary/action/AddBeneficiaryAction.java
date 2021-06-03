package com.finwin.cristal.custmate.home.transfer.add_beneficiary.action;

import com.finwin.cristal.custmate.home.transfer.add_beneficiary.pojo.AddBeneficiaryResponse;
import com.finwin.cristal.custmate.home.transfer.add_beneficiary.pojo.GetIfscResponse;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.get_account_holder.GetAccountHolderResponse;

public class AddBeneficiaryAction {
    public static final int DEFAULT=-1;
    public static final int GET_IFSC_SUCCESS=1;
    public static final int GET_IFSC_ERROR=2;
    public static final int API_ERROR=3;
    public static final int ADD_BENEFICIARY_SUCCESS=4;
    public static final int GET_ACCOUNT_HOLDER_SUCCESS=5;
    public static final int GET_ACCOUNT_HOLDER_ERROR=6;
    public static final int CLICK_BACK=7;
    public static final int CLICK_BENEFICIARY_LIST=8;
    public int action;
    public String error;
    public GetIfscResponse ifscResponse;
    public GetAccountHolderResponse accountHolderResponse;
    public AddBeneficiaryResponse addBeneficiaryResponse;

    public AddBeneficiaryAction(int action, AddBeneficiaryResponse addBeneficiaryResponse) {
        this.action = action;
        this.addBeneficiaryResponse = addBeneficiaryResponse;
    }

    public AddBeneficiaryAction(int action, GetAccountHolderResponse accountHolderResponse) {
        this.action = action;
        this.accountHolderResponse = accountHolderResponse;
    }

    public AddBeneficiaryAction(int action, GetIfscResponse getIfscResponse) {
        this.action = action;
        this.ifscResponse = getIfscResponse;
    }

    public AddBeneficiaryAction(int action) {
        this.action = action;
    }

    public AddBeneficiaryAction(int action, String error) {
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

    public GetIfscResponse getGetIfscResponse() {
        return ifscResponse;
    }

    public void setGetIfscResponse(GetIfscResponse getIfscResponse) {
        this.ifscResponse = getIfscResponse;
    }

    public GetAccountHolderResponse getAccountHolderResponse() {
        return accountHolderResponse;
    }

    public void setAccountHolderResponse(GetAccountHolderResponse accountHolderResponse) {
        this.accountHolderResponse = accountHolderResponse;
    }

    public GetIfscResponse getIfscResponse() {
        return ifscResponse;
    }

    public void setIfscResponse(GetIfscResponse ifscResponse) {
        this.ifscResponse = ifscResponse;
    }

    public AddBeneficiaryResponse getAddBeneficiaryResponse() {
        return addBeneficiaryResponse;
    }

    public void setAddBeneficiaryResponse(AddBeneficiaryResponse addBeneficiaryResponse) {
        this.addBeneficiaryResponse = addBeneficiaryResponse;
    }
}
