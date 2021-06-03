package com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.genarate_otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenarateOtpResponse {

@SerializedName("otp")
@Expose
private Otp otp;

public Otp getOtp() {
return otp;
}

public void setOtp(Otp otp) {
this.otp = otp;
}

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}