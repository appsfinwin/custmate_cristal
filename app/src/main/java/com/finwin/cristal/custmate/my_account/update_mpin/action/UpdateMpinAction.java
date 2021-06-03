package com.finwin.cristal.custmate.my_account.update_mpin.action;

import com.finwin.cristal.custmate.login.pojo.LoginResponse;
import com.finwin.cristal.custmate.my_account.update_mpin.pojo.UpdateMpinResponse;

public class UpdateMpinAction {
    public static final int DEFAULT=-1;
    public static final int API_ERROR=1;
    public static final int LOGIN_SUCCESS=2;
    public static final int MPIN_SUCCESS=3;
    public static final int MPIN_ERROR=4;
    int action;
    String error;
    public LoginResponse loginResponse;
    public UpdateMpinResponse updateMpinResponse;

    public UpdateMpinAction(int action, UpdateMpinResponse updateMpinResponse) {
        this.action = action;
        this.updateMpinResponse = updateMpinResponse;
    }

    public UpdateMpinAction(int action, LoginResponse loginResponse) {
        this.action = action;
        this.loginResponse = loginResponse;
    }

    public UpdateMpinAction(int action) {
        this.action = action;
    }

    public UpdateMpinAction(int action, String error) {
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

    public UpdateMpinResponse getUpdateMpinResponse() {
        return updateMpinResponse;
    }

    public void setUpdateMpinResponse(UpdateMpinResponse updateMpinResponse) {
        this.updateMpinResponse = updateMpinResponse;
    }
}
