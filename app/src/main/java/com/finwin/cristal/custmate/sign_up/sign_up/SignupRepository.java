package com.finwin.cristal.custmate.sign_up.sign_up;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.get_account_holder.GetAccountHolderResponse;
import com.finwin.cristal.custmate.pojo.Response;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.sign_up.sign_up.action.SignupAction;
import com.finwin.cristal.custmate.sign_up.sign_up.pojo.ApiKeyResponse;
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

public class SignupRepository {
    public static SignupRepository instance;
    public static SignupRepository getInstance()
    {
        if (instance==null)
        {
            instance=new SignupRepository();
        }
        return instance;
    }

    CompositeDisposable disposable;
    MutableLiveData<SignupAction> mAction;
    final Enc_crypter encr = new Enc_crypter();

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public MutableLiveData<SignupAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<SignupAction> mAction) {
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
//                            Gson gson = new GsonBuilder().create();
//                            Response apiKeyResponse = gson.fromJson(data, Response.class);

                            if (!data.equals(""))
                            {
                                mAction.setValue(new SignupAction(SignupAction.API_KEY_SUCCESS,data) );
                            }else
                            {
//                                String error=apiKeyResponse.getReceipt().getError();
                                mAction.setValue(new SignupAction(SignupAction.API_ERROR,"Something error"));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new SignupAction(SignupAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new SignupAction(SignupAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new SignupAction(SignupAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    public void getAccountHolder(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.getAccountHolder(body);
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
                            GetAccountHolderResponse getAccountHolderResponse = gson.fromJson(data, GetAccountHolderResponse.class);

                            if (getAccountHolderResponse.getAccount().getData()!=null)
                            {
                                mAction.setValue(new SignupAction(SignupAction.ACCOUNT_HOLDER_EXIST,getAccountHolderResponse));
                            }
                            else
                            {
                                String error=getAccountHolderResponse.getAccount().getError();
                                mAction.setValue(new SignupAction(SignupAction.ACCOUNT_HOLDER_NOT_EXIST,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new SignupAction(SignupAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new SignupAction(SignupAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new SignupAction(SignupAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
