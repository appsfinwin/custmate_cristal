package com.finwin.cristal.custmate.sign_up.otp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

@SerializedName("user")
@Expose
private User user;

public User getUser() {
return user;
}

public void setUser(User user) {
this.user = user;
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