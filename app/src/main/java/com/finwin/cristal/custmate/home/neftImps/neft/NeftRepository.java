package com.finwin.cristal.custmate.home.neftImps.neft;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.genarate_otp.GenarateOtpResponse;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.validate_mpin.ValidateMpinResponse;
import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryListResponse;
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

public class NeftRepository {
    public static NeftRepository instance;
    public static NeftRepository getInstance()
    {
        if (instance==null)
        {
            instance=new NeftRepository();
        }
        return instance;
    }

    CompositeDisposable compositeDisposable;
    MutableLiveData<NeftAction> mAction;
    Enc_crypter encr = new Enc_crypter();

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public MutableLiveData<NeftAction> getmAction() {
        return mAction;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public void setmAction(MutableLiveData<NeftAction> mAction) {
        this.mAction = mAction;
    }

    @SuppressLint("CheckResult")
    public void neftTransfer(ApiInterface apiInterface, RequestBody body)
    {
        Single<Response> call=apiInterface.neftTransfer(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response response) {
                        if (response.getData()!=null) {
                            //.setValue(new LoginAction(LoginAction.LOGIN_SUCCESS, loginResponse));

                        }else {
                           // actionMutableLiveData.setValue(new LoginAction(LoginAction.LOGIN_ERROR, loginResponse.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //actionMutableLiveData.setValue(new LoginAction(LoginAction.LOGIN_ERROR,e.getMessage()));
                    }
                });



    }

    public void getBeneficiary(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.getBeneficiary(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response response) {

                        try {
                            String data=decValues(encr.revDecString(response.getData()));
                            data=decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            BeneficiaryListResponse beneficiaryListResponse = gson.fromJson(data, BeneficiaryListResponse.class);

                            if (beneficiaryListResponse.getData()!=null)
                            {
                                mAction.setValue(new NeftAction(NeftAction.BENEFICIARY_LIST_SUCCESS,beneficiaryListResponse));
                                data=decValues(encr.revDecString(response.getData()));
                            }
                            else
                            {
                                String error=beneficiaryListResponse.getError();
                                mAction.setValue(new NeftAction(NeftAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new NeftAction(NeftAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new NeftAction(NeftAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new NeftAction(NeftAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }


    public void validateMpin(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.validateMpin(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response response) {

                        try {
                            String data = decValues(encr.revDecString(response.getData()));
                            data = decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            ValidateMpinResponse validateMpinResponse = gson.fromJson(data, ValidateMpinResponse.class);

                            if (validateMpinResponse.getValue() != null) {
                                mAction.setValue(new NeftAction(NeftAction.VALIDATE_MPIN_SUCCESS, validateMpinResponse));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException) {
                            mAction.setValue(new NeftAction(NeftAction.API_ERROR, "Timeout! Please try again later"));
                        } else if (e instanceof UnknownHostException) {
                            mAction.setValue(new NeftAction(NeftAction.API_ERROR, "No Internet"));
                        } else {
                            mAction.setValue(new NeftAction(NeftAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    public void generateOtp(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.generateOtp(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response response) {

                        try {
                            String data=decValues(encr.revDecString(response.getData()));
                            data=decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            GenarateOtpResponse genarateOtpResponse = gson.fromJson(data, GenarateOtpResponse.class);

                            if (genarateOtpResponse.getOtp()!=null)
                            {
                                mAction.setValue(new NeftAction(NeftAction.GENERATE_OTP_SUCCESS,genarateOtpResponse));
                            }
                            else
                            {
                                String error=genarateOtpResponse.getError();
                                mAction.setValue(new NeftAction(NeftAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new NeftAction(NeftAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new NeftAction(NeftAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new NeftAction(NeftAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
