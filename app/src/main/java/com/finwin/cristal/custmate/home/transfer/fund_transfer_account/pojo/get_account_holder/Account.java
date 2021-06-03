package com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.get_account_holder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {

@SerializedName("data")
@Expose
private Data data;

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
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