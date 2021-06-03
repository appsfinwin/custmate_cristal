package com.finwin.cristal.custmate.sign_up.sign_up.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("key")
@Expose
private String key;

public String getKey() {
return key;
}

public void setKey(String key) {
this.key = key;
}

}