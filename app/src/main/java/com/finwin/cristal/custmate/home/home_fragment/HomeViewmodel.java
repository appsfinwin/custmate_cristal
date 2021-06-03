package com.finwin.cristal.custmate.home.home_fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.home_fragment.action.HomeAction;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class HomeViewmodel extends AndroidViewModel {
    public HomeViewmodel(@NonNull Application application) {
        super(application);
        repository=HomeRepository.getInstance();
        mAction=new MutableLiveData<>();
        disposable=new CompositeDisposable();

        repository.setDisposable(disposable);
        repository.setmAction(mAction);
    }

    MutableLiveData<HomeAction> mAction;
    CompositeDisposable disposable;
    Enc_crypter encr = new Enc_crypter();
    HomeRepository repository;
    ApiInterface apiInterface;

    public MutableLiveData<HomeAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public void getMasters(Map<String, String> params) {


        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.getMasters(apiInterface, body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getDisposable()).dispose();
        mAction.setValue(new HomeAction(HomeAction.DEFAULT));
    }
}
