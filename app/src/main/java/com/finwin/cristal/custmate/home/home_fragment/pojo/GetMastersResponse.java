package com.finwin.cristal.custmate.home.home_fragment.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMastersResponse {

@SerializedName("masters")
@Expose
private Masters masters;

public Masters getMasters() {
return masters;
}

public void setMasters(Masters masters) {
this.masters = masters;
}

}