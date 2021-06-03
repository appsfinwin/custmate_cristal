package com.finwin.cristal.custmate.home.neftImps.neft;

import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.genarate_otp.GenarateOtpResponse;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.validate_mpin.ValidateMpinResponse;
import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryListResponse;

public class NeftAction {

    public static final int DEFAULT=-1;
    public static final int NEFT_TRANSFER_SUCCESS=1;
    public static final int ACCOUNT_TRANSFER_SUCCESS=2;
    public static final int API_ERROR=3;
    public static final int BENEFICIARY_LIST_SUCCESS=4;
    public static final int VALIDATE_MPIN_SUCCESS=5;
    public static final int GENERATE_OTP_SUCCESS=6;
    public static final int CLICK_PROCEED=7;

    public int action;
    public String error;
    public BeneficiaryListResponse beneficiaryListResponse;
    public ValidateMpinResponse validateMpinResponse;
    public GenarateOtpResponse genarateOtpResponse;

    public NeftAction(int action, GenarateOtpResponse genarateOtpResponse) {
        this.action = action;
        this.genarateOtpResponse = genarateOtpResponse;
    }

    public NeftAction(int action, ValidateMpinResponse validateMpinResponse) {
        this.action = action;
        this.validateMpinResponse = validateMpinResponse;
    }

    public NeftAction(int action, BeneficiaryListResponse beneficiaryListResponse) {
        this.action = action;
        this.beneficiaryListResponse = beneficiaryListResponse;
    }

    public NeftAction(int action) {
        this.action = action;
    }

    public NeftAction(int action, String error) {
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

    public BeneficiaryListResponse getBeneficiaryListResponse() {
        return beneficiaryListResponse;
    }

    public void setBeneficiaryListResponse(BeneficiaryListResponse beneficiaryListResponse) {
        this.beneficiaryListResponse = beneficiaryListResponse;
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


}
