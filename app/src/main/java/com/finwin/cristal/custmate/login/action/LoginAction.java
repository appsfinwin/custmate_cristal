package com.finwin.cristal.custmate.login.action;

import com.finwin.cristal.custmate.login.pojo.LoginResponse;

public class LoginAction {

    public static final int DEFAULT=-1;
    public static final int API_KEY_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int CLICK_SIGNUP=3;
    public static final int LOGIN_SUCCESS=4;
    public int action;
    public String error;
    public LoginResponse loginResponse;

    public LoginAction(int action, LoginResponse loginResponse) {
        this.action = action;
        this.loginResponse = loginResponse;
    }

    public LoginAction(int action) {
        this.action = action;
    }

    public LoginAction(int action, String error) {
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

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
