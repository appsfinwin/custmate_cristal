package com.finwin.cristal.custmate.home.transfer.transfer_account_otp;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.neftImps.pojo.neft_transfer.NeftTransferResponse;
import com.finwin.cristal.custmate.home.transfer.transfer_account_otp.pojo.resend_otp.ResendOtpResponse;
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

public class FundTransferOtpRepository {
    public static FundTransferOtpRepository instance;

    public static FundTransferOtpRepository getInstance() {
        if (instance == null) {
            instance = new FundTransferOtpRepository();
        }
        return instance;
    }

    MutableLiveData<FundTransferOtpAction> mAction;
    CompositeDisposable disposable;

    public MutableLiveData<FundTransferOtpAction> getmAction() {
        return mAction;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void setmAction(MutableLiveData<FundTransferOtpAction> mAction) {
        this.mAction = mAction;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }
    final Enc_crypter encr = new Enc_crypter();

    @SuppressLint("CheckResult")
    public void neftTransfer(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.neftTransfer(body);
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
                                Gson gson = new GsonBuilder().create();
                                NeftTransferResponse neftTransferResponse = gson.fromJson(data, NeftTransferResponse.class);

                                if (neftTransferResponse.getReceipt().getData()!=null)
                                {
                                    mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.NEFT_SUCCESS,neftTransferResponse));
                                }else
                                {
                                    String error=neftTransferResponse.getReceipt().getError();
                                    mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR,error));
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    public void accountTransfer(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.accountTransfer(body);
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
                            Gson gson = new GsonBuilder().create();
                            NeftTransferResponse neftTransferResponse = gson.fromJson(data, NeftTransferResponse.class);

                            if (neftTransferResponse.getReceipt().getData()!=null)
                            {
                                mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.ACCOUNT_TRANSFER_SUCCESS,neftTransferResponse));
                            }else
                            {
                                String error=neftTransferResponse.getReceipt().getError();
                                mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }


    public void resendOtp(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.resendOtp(body);
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
                            ResendOtpResponse resendOtpResponse = gson.fromJson(data, ResendOtpResponse.class);

                            if (resendOtpResponse.getOtp()!=null)
                            {
                                mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.OTP_SUCCESS,resendOtpResponse));
                            }else
                            {
                                String error=resendOtpResponse.getError();
                                mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new FundTransferOtpAction(FundTransferOtpAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }


}
