package com.finwin.cristal.custmate.mpin_register.action;

import com.finwin.cristal.custmate.login.pojo.LoginResponse;
import com.finwin.cristal.custmate.mpin_register.pojo.MpinRegisterResponse;

public class MpinRegisterAction {
    public static final int DEFAULT=-1;
    public static final int LOGIN_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int MPIN_REGISTER_SUCCESS=3;
    public static final int MPIN_REGISTER_ERROR=4;
    public int action;
    public String error;
    public LoginResponse loginResponse;
    public MpinRegisterResponse mpinRegisterResponse;

    public MpinRegisterAction(int action, MpinRegisterResponse mpinRegisterResponse) {
        this.action = action;
        this.mpinRegisterResponse = mpinRegisterResponse;
    }

    public MpinRegisterAction(int action, LoginResponse loginResponse) {
        this.action = action;
        this.loginResponse = loginResponse;
    }

    public MpinRegisterAction(int action) {
        this.action = action;
    }

    public MpinRegisterAction(int action, String error) {
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

    public MpinRegisterResponse getMpinRegisterResponse() {
        return mpinRegisterResponse;
    }

    public void setMpinRegisterResponse(MpinRegisterResponse mpinRegisterResponse) {
        this.mpinRegisterResponse = mpinRegisterResponse;
    }
}
