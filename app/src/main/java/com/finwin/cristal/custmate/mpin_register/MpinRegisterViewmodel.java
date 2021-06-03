package com.finwin.cristal.custmate.mpin_register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.mpin_register.action.MpinRegisterAction;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.Map;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MpinRegisterViewmodel extends AndroidViewModel {
    public MpinRegisterViewmodel(@NonNull Application application) {
        super(application);
        repository=MpinRegisterRepository.getInstance();
        mAction=new MutableLiveData<>();
        disposable=new CompositeDisposable();

        repository.setDisposable(disposable);
        repository.setmAction(mAction);
    }

    ApiInterface apiInterface;
    MpinRegisterRepository repository;
    MutableLiveData<MpinRegisterAction> mAction;
    CompositeDisposable disposable;
    final Enc_crypter encr = new Enc_crypter();

    public MutableLiveData<MpinRegisterAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public void validateUser(Map<String, String> params) {

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.validateUser(apiInterface, body);
    }
    public void registerMpin(Map<String, String> params) {

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.registerMpin(apiInterface, body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getDisposable()).dispose();
        mAction.setValue(new MpinRegisterAction(MpinRegisterAction.DEFAULT));
    }
}
