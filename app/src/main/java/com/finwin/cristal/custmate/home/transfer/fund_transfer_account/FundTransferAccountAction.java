package com.finwin.cristal.custmate.home.transfer.fund_transfer_account;

import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.genarate_otp.GenarateOtpResponse;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.get_account_holder.GetAccountHolderResponse;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.validate_mpin.ValidateMpinResponse;
import com.finwin.cristal.custmate.login.pojo.LoginResponse;

public class FundTransferAccountAction {
    public static final int DEFAULT=-1;
    public static final int GENERATE_OTP_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int VALIDATE_MPIN_SUCCESS=3;
    public static final int GET_ACCOUNT_HOLDER_SUCCESS=4;
    public static final int GET_CREDIT_ACCOUNT_HOLDER_SUCCESS=5;
    public static final int LOGIN_SUCCESS=6;
    public static final int CLICK_PROCEED=8;
    public static final int CLICK_SEARCH_BENEFICIARY=9;
    int action;
    String error;
    public ValidateMpinResponse validateMpinResponse;
    public GenarateOtpResponse genarateOtpResponse;
    public GetAccountHolderResponse getAccountHolderResponse;
    public LoginResponse loginResponse;

    public FundTransferAccountAction(int action, LoginResponse loginResponse) {
        this.action = action;
        this.loginResponse = loginResponse;
    }

    public FundTransferAccountAction(int action, GetAccountHolderResponse getAccountHolderResponse) {
        this.action = action;
        this.getAccountHolderResponse = getAccountHolderResponse;
    }

    public FundTransferAccountAction(int action, GenarateOtpResponse genarateOtpResponse) {
        this.action = action;
        this.genarateOtpResponse = genarateOtpResponse;
    }

    public FundTransferAccountAction(int action, ValidateMpinResponse validateMpinResponse) {
        this.action = action;
        this.validateMpinResponse = validateMpinResponse;
    }

    public FundTransferAccountAction(int action) {
        this.action = action;
    }

    public FundTransferAccountAction(int action, String error) {
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

    public ValidateMpinResponse getValidateMpinResponse() {
        return validateMpinResponse;
    }

    public void setValidateMpinResponse(ValidateMpinResponse validateMpinResponse) {
        this.validateMpinResponse = validateMpinResponse;
    }

    public GenarateOtpResponse getGenarateOtpResponse() {
        return genarateOtpResponse;
    }

    public void setGenarateOtpResponse(GenarateOtpResponse genarateOtpResponse) {
        this.genarateOtpResponse = genarateOtpResponse;
    }

    public GetAccountHolderResponse getGetAccountHolderResponse() {
        return getAccountHolderResponse;
    }

    public void setGetAccountHolderResponse(GetAccountHolderResponse getAccountHolderResponse) {
        this.getAccountHolderResponse = getAccountHolderResponse;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
