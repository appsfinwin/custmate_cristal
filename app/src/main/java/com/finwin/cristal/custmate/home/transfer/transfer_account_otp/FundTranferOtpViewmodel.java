package com.finwin.cristal.custmate.home.transfer.transfer_account_otp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FundTranferOtpViewmodel extends AndroidViewModel {
    public FundTranferOtpViewmodel(@NonNull Application application) {
        super(application);

        repository=FundTransferOtpRepository.getInstance();
        mAction=new MutableLiveData<>();
        disposable=new CompositeDisposable();

        repository.setDisposable(disposable);
        repository.setmAction(mAction);

    }

    public MutableLiveData<FundTransferOtpAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    ApiInterface apiInterface;
    FundTransferOtpRepository repository;
    MutableLiveData<FundTransferOtpAction> mAction;
    CompositeDisposable disposable;


    public void neftApi(Map<String, String> params)
    {
        Map<String, Object> jsonParams = new HashMap<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.neftTransfer(apiInterface,body);
    }

    public void accountTransfer(Map<String, String> params)
    {
        Map<String, Object> jsonParams = new HashMap<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.accountTransfer(apiInterface,body);
    }

    public void resendOtp(Map<String, String> params)
    {
        Map<String, Object> jsonParams = new HashMap<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.resendOtp(apiInterface,body);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getDisposable()).dispose();
        mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.DEFAULT));
    }
}
