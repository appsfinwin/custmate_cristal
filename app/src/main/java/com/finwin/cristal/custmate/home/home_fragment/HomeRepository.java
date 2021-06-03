package com.finwin.cristal.custmate.home.home_fragment;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.home_fragment.action.HomeAction;

import com.finwin.cristal.custmate.home.home_fragment.pojo.GetMastersResponse;
import com.finwin.cristal.custmate.pojo.Response;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class HomeRepository {
    public static HomeRepository instance;
    public static HomeRepository getInstance()
    {
        if (instance==null)
        {
            instance=new HomeRepository();
        }
        return instance;
    }
    
    MutableLiveData<HomeAction> mAction;
    CompositeDisposable disposable;
    Enc_crypter encr = new Enc_crypter();

    public MutableLiveData<HomeAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<HomeAction> mAction) {
        this.mAction = mAction;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public void getMasters(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.getMasters(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response response) {

                        try {
                            String data= Enc_Utils.decValues(encr.revDecString(response.getData()));
                            data= Enc_Utils.decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            GetMastersResponse mastersResponse = gson.fromJson(data, GetMastersResponse.class);
                            if (mastersResponse.getMasters()!=null)
                            {
                                mAction.setValue(new HomeAction(HomeAction.GET_MASTERS_SUCCESS,mastersResponse));
                                  }else {
                                mAction.setValue(new HomeAction(HomeAction.GET_MASTERS_ERROR,mastersResponse.getMasters().getMsg()));
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new HomeAction(HomeAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new HomeAction(HomeAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new HomeAction(HomeAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
