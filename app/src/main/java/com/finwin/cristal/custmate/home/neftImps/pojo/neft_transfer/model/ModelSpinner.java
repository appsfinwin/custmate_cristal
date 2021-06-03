package com.finwin.cristal.custmate.home.neftImps.pojo.neft_transfer.model;

public class ModelSpinner {
    String account_number,ifsc,ben_id;

    public ModelSpinner(String account_number, String ifsc, String ben_id) {
        this.account_number = account_number;
        this.ifsc = ifsc;
        this.ben_id = ben_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBen_id() {
        return ben_id;
    }

    public void setBen_id(String ben_id) {
        this.ben_id = ben_id;
    }
}
