package com.finwin.cristal.custmate.sign_up.otp.action;

import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.genarate_otp.GenarateOtpResponse;
import com.finwin.cristal.custmate.sign_up.otp.pojo.RegisterResponse;

public class SignupOtpAction {
    public static final int DEFAULT=-1;
    public static final int GENERATE_OTP_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int CLICK_PROCEED=3;
    public static final int REGISTER_SUCCESS=4;
    public static final int SIGNUP_ERROR=5;
    public int action;
    public String error;
    public GenarateOtpResponse genarateOtpResponse;
    public RegisterResponse registerResponse;

    public SignupOtpAction(int action, RegisterResponse registerResponse) {
        this.action = action;
        this.registerResponse = registerResponse;
    }

    public SignupOtpAction(int action, GenarateOtpResponse genarateOtpResponse) {
        this.action = action;
        this.genarateOtpResponse = genarateOtpResponse;
    }

    public SignupOtpAction(int action) {
        this.action = action;
    }

    public SignupOtpAction(int action, String error) {
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

    public GenarateOtpResponse getGenarateOtpResponse() {
        return genarateOtpResponse;
    }

    public void setGenarateOtpResponse(GenarateOtpResponse genarateOtpResponse) {
        this.genarateOtpResponse = genarateOtpResponse;
    }

    public RegisterResponse getRegisterResponse() {
        return registerResponse;
    }

    public void setRegisterResponse(RegisterResponse registerResponse) {
        this.registerResponse = registerResponse;
    }
}
