package com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.genarate_otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Otp {

@SerializedName("data")
@Expose
private String data;
@SerializedName("otp_id")
@Expose
private String otpId;

public String getData() {
return data;
}

public void setData(String data) {
this.data = data;
}

public String getOtpId() {
return otpId;
}

public void setOtpId(String otpId) {
this.otpId = otpId;
}

}