package com.finwin.cristal.custmate.AdapterAndModel.Models;

public class MiniStmntListModel {
    private String txnid, date, trnsacTyp, debitORCre, amount, balance;

    public MiniStmntListModel(String _txnid, String _date, String _trnsacTyp, String _debitORCre, String _amount, String _balance) {
        this.txnid = _txnid;
        this.date = _date;
        this.trnsacTyp = _trnsacTyp;
        this.debitORCre = _debitORCre;
        this.amount = _amount;
        this.balance = _balance;
    }

//    public Integer getIconImage() {
//        return iconImage;
//    }
//
//    public void setIconImage(Integer iconImage) {
//        this.iconImage = iconImage;
//    }


    public String getTranID() {
        return txnid;
    }

    public void setTranID(String _txnid) {
        this.txnid = _txnid;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String _date) {
        this.date = _date;
    }


    public String getTrnsacTyp() {
        return trnsacTyp;
    }

    public void setTrnsacTyp(String _trnsacTyp) {
        this.trnsacTyp = _trnsacTyp;
    }


    public String getDebitORCre() {
        return debitORCre;
    }

    public void setDebitORCre(String _debitORCre) {
        this.debitORCre = _debitORCre;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getBalance() {
        return balance;
    }

    public void setBalance(String _balance) {
        this.balance = _balance;
    }

}
