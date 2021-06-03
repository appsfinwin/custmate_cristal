package com.finwin.cristal.custmate.login;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.login.action.LoginAction;
import com.finwin.cristal.custmate.login.pojo.LoginResponse;
import com.finwin.cristal.custmate.pojo.Response;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.sign_up.sign_up.pojo.ApiKeyResponse;
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

public class LoginRepository {
    public static LoginRepository instance;
    public static LoginRepository getInstance()
    {
        if (instance==null)
        {
            instance=new LoginRepository();
        }
        return instance;
    }

    CompositeDisposable disposable;
    MutableLiveData<LoginAction> mAction;


    final Enc_crypter encr = new Enc_crypter();
    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public MutableLiveData<LoginAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<LoginAction> mAction) {
        this.mAction = mAction;
    }

    public void getApiKey(ApiInterface apiInterface) {
        Single<ApiKeyResponse> call = apiInterface.getApiKey();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiKeyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(ApiKeyResponse response) {

                        try {
                            String data=encr.revDecString(response.getData().getKey());

                            if (!data.equals(""))
                            {
                                mAction.setValue(new LoginAction(LoginAction.API_KEY_SUCCESS,data) );
                            }else
                            {
//                                String error=apiKeyResponse.getReceipt().getError();
                                mAction.setValue(new LoginAction(LoginAction.API_ERROR,"Something error"));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    public void login(ApiInterface apiInterface, RequestBody body) {
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
                                mAction.setValue(new LoginAction(LoginAction.LOGIN_SUCCESS,loginResponse));
                            }
                            else
                            {
                                String error=loginResponse.getUser().getError();
                                mAction.setValue(new LoginAction(LoginAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
