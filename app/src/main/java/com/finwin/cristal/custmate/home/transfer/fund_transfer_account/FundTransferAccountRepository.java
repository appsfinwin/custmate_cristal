package com.finwin.cristal.custmate.home.transfer.fund_transfer_account;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.genarate_otp.GenarateOtpResponse;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.get_account_holder.GetAccountHolderResponse;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.validate_mpin.ValidateMpinResponse;
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

public class FundTransferAccountRepository {

    public static FundTransferAccountRepository instance;
    public static FundTransferAccountRepository getInstance()
    {
        if (instance==null)
        {
            instance=new FundTransferAccountRepository();
        }
        return instance;
    }

    CompositeDisposable disposable;
    MutableLiveData<FundTransferAccountAction> mAction;

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public MutableLiveData<FundTransferAccountAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<FundTransferAccountAction> mAction) {
        this.mAction = mAction;
    }

    final Enc_crypter encr = new Enc_crypter();

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
                            String data=decValues(encr.revDecString(response.getData()));
                            data=decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            GenarateOtpResponse genarateOtpResponse = gson.fromJson(data, GenarateOtpResponse.class);

                            if (genarateOtpResponse.getOtp()!=null)
                            {
                                mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.GENERATE_OTP_SUCCESS,genarateOtpResponse));
                            }
                            else
                            {
                                String error=genarateOtpResponse.getError();
                                mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR, e.getMessage()));
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
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response response) {

                        try {
                            String data=decValues(encr.revDecString(response.getData()));
                            data=decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                             ValidateMpinResponse validateMpinResponse = gson.fromJson(data, ValidateMpinResponse.class);

                            if (validateMpinResponse.getValue()!=null)
                            {
                                mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.VALIDATE_MPIN_SUCCESS,validateMpinResponse));
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR, e.getMessage()));
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
                            String data=decValues(encr.revDecString(response.getData()));
                            data=decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            GetAccountHolderResponse getAccountHolderResponse = gson.fromJson(data, GetAccountHolderResponse.class);

                            if (getAccountHolderResponse.getAccount().getData()!=null)
                            {
                                mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.GET_ACCOUNT_HOLDER_SUCCESS,getAccountHolderResponse));
                            }
                            else
                            {
                                String error=getAccountHolderResponse.getAccount().getError();
                                mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    public void getCreditAccountHolder(ApiInterface apiInterface, RequestBody body) {
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
                            String data=decValues(encr.revDecString(response.getData()));
                            data=decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            GetAccountHolderResponse accountHolderResponse = gson.fromJson(data, GetAccountHolderResponse.class);

                            if (accountHolderResponse.getAccount().getData()!=null)
                            {
                                mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.GET_CREDIT_ACCOUNT_HOLDER_SUCCESS,accountHolderResponse));
                            }
                            else
                            {
                                String error=accountHolderResponse.getAccount().getError();
                                mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
