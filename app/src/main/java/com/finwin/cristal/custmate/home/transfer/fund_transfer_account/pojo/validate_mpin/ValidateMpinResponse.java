package com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.validate_mpin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateMpinResponse {

@SerializedName("value")
@Expose
private Boolean value;

public Boolean getValue() {
return value;
}

public void setValue(Boolean value) {
this.value = value;
}

}