package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.transaction_details;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_status.TransactionStatusData;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TransactionDetailsViewmodel extends AndroidViewModel {
    public TransactionDetailsViewmodel(@NonNull Application application) {
        super(application);
        repository=TransactionRepository.getInstance();
        disposable=new CompositeDisposable();
        mAction=new MutableLiveData<>();

        repository.setDisposable(disposable);
        repository.setmAction(mAction);
    }
    Enc_crypter encr = new Enc_crypter();

    ApiInterface apiInterface;
    TransactionRepository repository;
    MutableLiveData<TransactionDetailsAction> mAction;
    CompositeDisposable disposable;

    public LiveData<TransactionDetailsAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public ObservableField<String> transactionIdDigicob=new ObservableField<>("");
    public ObservableField<String> transactionDate=new ObservableField<>("");
    public ObservableField<String> name=new ObservableField<>("");
    public ObservableField<String> accountNo=new ObservableField<>("");
    public ObservableField<String> mobile=new ObservableField<>("");
    public ObservableField<String> customerId=new ObservableField<>("");
    public ObservableField<String> bankReferenceNo=new ObservableField<>("");
    public ObservableField<String> creditAccountNumber=new ObservableField<>("");
    public ObservableField<String> amount=new ObservableField<>("");
    public ObservableField<String> status=new ObservableField<>("");
    public ObservableField<String> creditDebit=new ObservableField<>("");

    public void getRecentTransactionStatus(String txn_no) {
        Map<String, Object> jsonParams = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        jsonParams.put("txn_no", txn_no);

        params.put("data", encr.conRevString(Enc_Utils.enValues(jsonParams)));

        String request=new JSONObject(params).toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getTransactionStatus(apiInterface,body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getDisposable()).dispose();
        mAction.setValue(new TransactionDetailsAction(TransactionDetailsAction.DEFAULT));
    }

    public void setData(TransactionStatusData data) {
        transactionIdDigicob.set(data.getTRANIDDIGICOB());
        transactionDate.set(data.getPaymentRunDate());
        name.set(data.getNAME());
        accountNo.set(data.getACCNO());
        mobile.set(data.getMOBILE());
        customerId.set(data.getCustomerUniqueNo());
        bankReferenceNo.set(data.getBankReferenceNumber());
        amount.set(data.getAmount());
        status.set(data.getStatusCode() +" ("+data.getStatusDescription()+")");
        creditDebit.set(data.getDebitCreditIndicator());
        creditAccountNumber.set(data.getBeneficiaryAccountNumber());
    }

    public void backPressed(View view)
    {
        mAction.setValue(new TransactionDetailsAction(TransactionDetailsAction.BACK_PRESSED));
    }
}
