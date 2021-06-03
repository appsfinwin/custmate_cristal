package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionData {

@SerializedName("Rec_Id")
@Expose
private String recId;
@SerializedName("TRAN_ID_DIGICOB")
@Expose
private String tRANIDDIGICOB;
@SerializedName("ACC_NO")
@Expose
private String aCCNO;
@SerializedName("CUST_ID")
@Expose
private String cUSTID;
@SerializedName("NAME")
@Expose
private String nAME;
@SerializedName("MOBILE")
@Expose
private String mOBILE;
@SerializedName("Customer_Unique_No")
@Expose
private String customerUniqueNo;
@SerializedName("Corp_Code")
@Expose
private String corpCode;
@SerializedName("Payment_Run_Date")
@Expose
private String paymentRunDate;
@SerializedName("Transaction_UTR_number")
@Expose
private String transactionUTRNumber;
@SerializedName("Status_Code")
@Expose
private String statusCode;
@SerializedName("Status_Description")
@Expose
private String statusDescription;
@SerializedName("Bank_Reference_Number")
@Expose
private String bankReferenceNumber;
@SerializedName("Amount")
@Expose
private String amount;
@SerializedName("Debit_Credit_Indicator")
@Expose
private String debitCreditIndicator;
@SerializedName("Beneficiary_Account_Number")
@Expose
private String beneficiaryAccountNumber;

public String getRecId() {
return recId;
}

public void setRecId(String recId) {
this.recId = recId;
}

public String getTRANIDDIGICOB() {
return tRANIDDIGICOB;
}

public void setTRANIDDIGICOB(String tRANIDDIGICOB) {
this.tRANIDDIGICOB = tRANIDDIGICOB;
}

public String getACCNO() {
return aCCNO;
}

public void setACCNO(String aCCNO) {
this.aCCNO = aCCNO;
}

public String getCUSTID() {
return cUSTID;
}

public void setCUSTID(String cUSTID) {
this.cUSTID = cUSTID;
}

public String getNAME() {
return nAME;
}

public void setNAME(String nAME) {
this.nAME = nAME;
}

public String getMOBILE() {
return mOBILE;
}

public void setMOBILE(String mOBILE) {
this.mOBILE = mOBILE;
}

public String getCustomerUniqueNo() {
return customerUniqueNo;
}

public void setCustomerUniqueNo(String customerUniqueNo) {
this.customerUniqueNo = customerUniqueNo;
}

public String getCorpCode() {
return corpCode;
}

public void setCorpCode(String corpCode) {
this.corpCode = corpCode;
}

public String getPaymentRunDate() {
return paymentRunDate;
}

public void setPaymentRunDate(String paymentRunDate) {
this.paymentRunDate = paymentRunDate;
}

public String getTransactionUTRNumber() {
return transactionUTRNumber;
}

public void setTransactionUTRNumber(String transactionUTRNumber) {
this.transactionUTRNumber = transactionUTRNumber;
}

public String getStatusCode() {
return statusCode;
}

public void setStatusCode(String statusCode) {
this.statusCode = statusCode;
}

public String getStatusDescription() {
return statusDescription;
}

public void setStatusDescription(String statusDescription) {
this.statusDescription = statusDescription;
}

public String getBankReferenceNumber() {
return bankReferenceNumber;
}

public void setBankReferenceNumber(String bankReferenceNumber) {
this.bankReferenceNumber = bankReferenceNumber;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

public String getDebitCreditIndicator() {
return debitCreditIndicator;
}

public void setDebitCreditIndicator(String debitCreditIndicator) {
this.debitCreditIndicator = debitCreditIndicator;
}

public String getBeneficiaryAccountNumber() {
return beneficiaryAccountNumber;
}

public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
this.beneficiaryAccountNumber = beneficiaryAccountNumber;
}

}