package com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.adapter;

import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryData;

public class BeneficiaryRowAction {
    public static final int CLICK_DELETE=1;
    int action;
    BeneficiaryData data;

    public BeneficiaryRowAction(int action, BeneficiaryData data) {
        this.action = action;
        this.data = data;
    }

    public BeneficiaryRowAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public BeneficiaryData getData() {
        return data;
    }

    public void setData(BeneficiaryData data) {
        this.data = data;
    }
}
