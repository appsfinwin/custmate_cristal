package com.finwin.cristal.custmate.my_account.update_mpin;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.login.pojo.LoginResponse;
import com.finwin.cristal.custmate.my_account.update_mpin.action.UpdateMpinAction;
import com.finwin.cristal.custmate.my_account.update_mpin.pojo.UpdateMpinResponse;
import com.finwin.cristal.custmate.pojo.Response;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
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

import static com.finwin.cristal.custmate.SupportingClass.Enc_Utils.decValues;

public class UpdateMpinRepository {
    public static UpdateMpinRepository instance;
    public static UpdateMpinRepository getInstance()
    {
        if (instance==null)
        {
            instance=new UpdateMpinRepository();
        }
        return instance;
    }

    MutableLiveData<UpdateMpinAction> mAction;
    CompositeDisposable disposable;
    final Enc_crypter encr = new Enc_crypter();
    public MutableLiveData<UpdateMpinAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<UpdateMpinAction> mAction) {
        this.mAction = mAction;
    }

    public CompositeDisposable getCompositeDisposable() {
        return disposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.disposable = compositeDisposable;
    }

    public void validateUser(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.login(body);
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
                            String data=decValues(encr.revDecString(response.getData()));
                            data=decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            LoginResponse loginResponse = gson.fromJson(data, LoginResponse.class);

                            if (loginResponse.getUser().getData()!=null)
                            {
                                mAction.setValue(new UpdateMpinAction(UpdateMpinAction.LOGIN_SUCCESS,loginResponse));
                            }
                            else
                            {
                                String error=loginResponse.getUser().getError();
                                mAction.setValue(new UpdateMpinAction(UpdateMpinAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new UpdateMpinAction(UpdateMpinAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new UpdateMpinAction(UpdateMpinAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new UpdateMpinAction(UpdateMpinAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    public void validateMpin(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.updateMpin(body);
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
                            String data=decValues(encr.revDecString(response.getData()));
                            data=decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            UpdateMpinResponse updateMpinResponse = gson.fromJson(data, UpdateMpinResponse.class);

                            if (updateMpinResponse.getStatus()!=null)
                            {
                                mAction.setValue(new UpdateMpinAction(UpdateMpinAction.MPIN_SUCCESS,updateMpinResponse));
                            }
                            else
                            {
                                String error=updateMpinResponse.getError();
                                mAction.setValue(new UpdateMpinAction(UpdateMpinAction.MPIN_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new UpdateMpinAction(UpdateMpinAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new UpdateMpinAction(UpdateMpinAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new UpdateMpinAction(UpdateMpinAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
