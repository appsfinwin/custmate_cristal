package com.finwin.cristal.custmate.home.home_fragment.action;

import com.finwin.cristal.custmate.home.home_fragment.pojo.GetMastersResponse;

public class HomeAction {

    public static final int DEFAULT=-1;
    public static final int GET_MASTERS_SUCCESS=1;
    public static final int GET_MASTERS_ERROR=2;
    public static final int API_ERROR=3;
    public int action;
    public String error;
    public GetMastersResponse getMastersResponse;

    public HomeAction(int action, GetMastersResponse getMastersResponse) {
        this.action = action;
        this.getMastersResponse = getMastersResponse;
    }

    public HomeAction(int action) {
        this.action = action;
    }

    public HomeAction(int action, String error) {
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

    public GetMastersResponse getGetMastersResponse() {
        return getMastersResponse;
    }

    public void setGetMastersResponse(GetMastersResponse getMastersResponse) {
        this.getMastersResponse = getMastersResponse;
    }
}
