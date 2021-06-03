package com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeneficiaryListResponse {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("data")
@Expose
private List<BeneficiaryData> data = null;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public List<BeneficiaryData> getData() {
return data;
}

public void setData(List<BeneficiaryData> data) {
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