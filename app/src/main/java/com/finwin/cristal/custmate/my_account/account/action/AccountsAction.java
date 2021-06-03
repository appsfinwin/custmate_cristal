package com.finwin.cristal.custmate.my_account.account.action;

public class AccountsAction {
    public static final int DEFAULT=-1;
    public int action;
    public String error;

    public AccountsAction(int action) {
        this.action = action;
    }

    public AccountsAction(int action, String error) {
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
}
