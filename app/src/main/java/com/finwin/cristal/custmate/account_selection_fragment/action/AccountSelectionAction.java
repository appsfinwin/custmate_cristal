package com.finwin.cristal.custmate.account_selection_fragment.action;

import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.get_account_holder.GetAccountHolderResponse;

public class AccountSelectionAction {
    public static final int DEFAULT=-1;
    public static final int API_ERROR=1;
    public static final int GET_ACCOUNT_HOLDER_SUCCESS=2;
    public static final int GET_ACCOUNT_HOLDER_ERROR=3;
    int action;
    String error;
    public GetAccountHolderResponse getAccountHolderResponse;

    public AccountSelectionAction(int action, GetAccountHolderResponse getAccountHolderResponse) {
        this.action = action;
        this.getAccountHolderResponse = getAccountHolderResponse;
    }

    public AccountSelectionAction(int action) {
        this.action = action;
    }

    public AccountSelectionAction(int action, String error) {
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

    public GetAccountHolderResponse getGetAccountHolderResponse() {
        return getAccountHolderResponse;
    }

    public void setGetAccountHolderResponse(GetAccountHolderResponse getAccountHolderResponse) {
        this.getAccountHolderResponse = getAccountHolderResponse;
    }
}
