package com.finwin.cristal.custmate.home.balance_enquiry.action;

import com.finwin.cristal.custmate.home.balance_enquiry.pojo.BalanceResponse;

public class BalanceAction {
    public static final int  DEFAULT=-1;
    public static final int  API_ERROR=1;
    public static final int  GET_BALANCE_SUCCESS=2;
    public static final int  CLICK_BACK=3;
    public int action;
    public String error;
    public BalanceResponse balanceResponse;

    public BalanceAction(int action, BalanceResponse balanceResponse) {
        this.action = action;
        this.balanceResponse = balanceResponse;
    }

    public BalanceAction(int action) {
        this.action = action;
    }

    public BalanceAction(int action, String error) {
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

    public BalanceResponse getBalanceResponse() {
        return balanceResponse;
    }

    public void setBalanceResponse(BalanceResponse balanceResponse) {
        this.balanceResponse = balanceResponse;
    }
}
