package com.finwin.cristal.custmate.home.transfer.transfer_account_otp;

import com.finwin.cristal.custmate.home.neftImps.pojo.neft_transfer.NeftTransferResponse;
import com.finwin.cristal.custmate.home.transfer.transfer_account_otp.pojo.resend_otp.ResendOtpResponse;

public class FundTransferOtpAction {

    public static final int DEFAULT=-1;
    public static final int NEFT_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int ACCOUNT_TRANSFER_SUCCESS=3;
    public static final int OTP_SUCCESS=4;
    int action;
    String error;
    public NeftTransferResponse neftTransferResponse;
    public ResendOtpResponse resendOtpResponse;

    public FundTransferOtpAction(int action, ResendOtpResponse resendOtpResponse) {
        this.action = action;
        this.resendOtpResponse = resendOtpResponse;
    }

    public FundTransferOtpAction(int action) {
        this.action = action;
    }

    public FundTransferOtpAction(int action, NeftTransferResponse neftTransferResponse) {
        this.action = action;
        this.neftTransferResponse = neftTransferResponse;
    }

    public FundTransferOtpAction(int action, String error) {
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

    public NeftTransferResponse getNeftTransferResponse() {
        return neftTransferResponse;
    }

    public ResendOtpResponse getResendOtpResponse() {
        return resendOtpResponse;
    }

    public void setResendOtpResponse(ResendOtpResponse resendOtpResponse) {
        this.resendOtpResponse = resendOtpResponse;
    }

    public void setNeftTransferResponse(NeftTransferResponse neftTransferResponse) {
        this.neftTransferResponse = neftTransferResponse;


    }
}
