package com.finwin.cristal.custmate.sign_up.otp;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.genarate_otp.GenarateOtpResponse;
import com.finwin.cristal.custmate.pojo.Response;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.sign_up.otp.action.SignupOtpAction;
import com.finwin.cristal.custmate.sign_up.otp.pojo.RegisterResponse;
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

public class SignupOtpRepository {
    public static SignupOtpRepository instance;
    public static SignupOtpRepository getInstance()
    {
        if (instance==null)
        {
            instance=new SignupOtpRepository();
        }
        return instance;
    }

    CompositeDisposable disposable;
    MutableLiveData<SignupOtpAction> mAction;
    final Enc_crypter encr = new Enc_crypter();
    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public MutableLiveData<SignupOtpAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<SignupOtpAction> mAction) {
        this.mAction = mAction;
    }

    public void generateOtp(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.generateOtp(body);
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
                            GenarateOtpResponse genarateOtpResponse = gson.fromJson(data, GenarateOtpResponse.class);

                            if (genarateOtpResponse.getOtp()!=null)
                            {
                                mAction.setValue(new SignupOtpAction(SignupOtpAction.GENERATE_OTP_SUCCESS,genarateOtpResponse));
                            }
                            else
                            {
                                String error=genarateOtpResponse.getError();
                                mAction.setValue(new SignupOtpAction(SignupOtpAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new SignupOtpAction(SignupOtpAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new SignupOtpAction(SignupOtpAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new SignupOtpAction(SignupOtpAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    public void userRegister(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.userRegister(body);
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
                            RegisterResponse registerResponse = gson.fromJson(data, RegisterResponse.class);

                            if (registerResponse.getUser()!=null)
                            {
                                mAction.setValue(new SignupOtpAction(SignupOtpAction.REGISTER_SUCCESS,registerResponse));
                            }
                            else
                            {
                                String error=registerResponse.getError();
                                mAction.setValue(new SignupOtpAction(SignupOtpAction.SIGNUP_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new SignupOtpAction(SignupOtpAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new SignupOtpAction(SignupOtpAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new SignupOtpAction(SignupOtpAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
