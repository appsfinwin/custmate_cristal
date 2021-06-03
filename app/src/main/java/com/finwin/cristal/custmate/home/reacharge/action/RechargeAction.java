package com.finwin.cristal.custmate.home.reacharge.action;

import com.finwin.cristal.custmate.home.reacharge.pojo.RechargeResponse;
import com.finwin.cristal.custmate.home.reacharge.pojo.get_circle_response.GetCircleResponse;
import com.finwin.cristal.custmate.home.reacharge.pojo.get_operator_response.GetOperatorResponse;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.validate_mpin.ValidateMpinResponse;

public class RechargeAction {
    public static final int  DEFAULT=-1;
    public static final int  API_ERROR=1;
    public static final int  GET_CIRCLE_SUCCESS=2;
    public static final int  GET_CIRCLE_ERROR=3;
    public static final int  GET_OPERATOR_SUCCESS=4;
    public static final int  GET_OPERATOR_ERROR=5;
    public static final int  VALIDATE_MPIN_SUCCESS=6;
    public static final int  CLICK_BACK=7;
    public static final int  VALIDATE_MPIN_ERROR=8;
    public static final int  RECHARGE_SUCCESS=9;
    public static final int  RECHARGE_ERROR=10;
    public static final int  CLICK_PROCEED=11;
    public int action;
    String error;
    public GetCircleResponse getCircleResponse;
    public GetOperatorResponse getOperatorResponse;
    public ValidateMpinResponse validateMpinResponse;
    public RechargeResponse rechargeResponse;

    public RechargeAction(int action, RechargeResponse rechargeResponse) {
        this.action = action;
        this.rechargeResponse = rechargeResponse;
    }

    public RechargeAction(int action, ValidateMpinResponse validateMpinResponse) {
        this.action = action;
        this.validateMpinResponse = validateMpinResponse;
    }

    public RechargeAction(int action, GetOperatorResponse getOperatorResponse) {
        this.action = action;
        this.getOperatorResponse = getOperatorResponse;
    }

    public RechargeAction(int action, GetCircleResponse getCircleResponse) {
        this.action = action;
        this.getCircleResponse = getCircleResponse;
    }

    public RechargeAction(int action) {
        this.action = action;
    }

    public RechargeAction(int action, String error) {
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

    public GetCircleResponse getGetCircleResponse() {
        return getCircleResponse;
    }

    public void setGetCircleResponse(GetCircleResponse getCircleResponse) {
        this.getCircleResponse = getCircleResponse;
    }

    public GetOperatorResponse getGetOperatorResponse() {
        return getOperatorResponse;
    }

    public void setGetOperatorResponse(GetOperatorResponse getOperatorResponse) {
        this.getOperatorResponse = getOperatorResponse;
    }

    public ValidateMpinResponse getValidateMpinResponse() {
        return validateMpinResponse;
    }

    public void setValidateMpinResponse(ValidateMpinResponse validateMpinResponse) {
        this.validateMpinResponse = validateMpinResponse;
    }

    public RechargeResponse getRechargeResponse() {
        return rechargeResponse;
    }

    public void setRechargeResponse(RechargeResponse rechargeResponse) {
        this.rechargeResponse = rechargeResponse;
    }
}
