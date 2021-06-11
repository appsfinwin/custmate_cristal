package com.finwin.cristal.custmate.home.reacharge;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.reacharge.action.RechargeAction;
import com.finwin.cristal.custmate.home.reacharge.pojo.RechargeResponse;
import com.finwin.cristal.custmate.home.reacharge.pojo.get_circle_response.GetCircleResponse;
import com.finwin.cristal.custmate.home.reacharge.pojo.get_operator_response.GetOperatorResponse;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.validate_mpin.ValidateMpinResponse;
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

public class RechargeRepository {

    public static RechargeRepository instance;

    public static RechargeRepository getInstance() {
        if (instance == null) {
            instance = new RechargeRepository();
        }
        return instance;
    }

    MutableLiveData<RechargeAction> mAction;
    CompositeDisposable disposable;
    Enc_crypter encr = new Enc_crypter();

    public MutableLiveData<RechargeAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<RechargeAction> mAction) {
        this.mAction = mAction;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public void getOperatorList(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.getOperatorList(body);
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
                            String data = Enc_Utils.decValues(encr.revDecString(response.getData()));
                            data = Enc_Utils.decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            GetOperatorResponse getOperatorResponse = gson.fromJson(data, GetOperatorResponse.class);

                            if (getOperatorResponse.getData() != null) {
                                mAction.setValue(new RechargeAction(RechargeAction.GET_OPERATOR_SUCCESS, getOperatorResponse));
                            } else {
                                String error = getOperatorResponse.getMsg();
                                mAction.setValue(new RechargeAction(RechargeAction.GET_OPERATOR_ERROR, error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException) {
                            mAction.setValue(new RechargeAction(RechargeAction.GET_OPERATOR_ERROR, "Timeout! Please try again later"));
                        } else if (e instanceof UnknownHostException) {
                            mAction.setValue(new RechargeAction(RechargeAction.GET_OPERATOR_ERROR, "No Internet"));
                        } else {
                            mAction.setValue(new RechargeAction(RechargeAction.GET_OPERATOR_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    public void getCircle(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.getCircle(body);
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
                            String data = Enc_Utils.decValues(encr.revDecString(response.getData()));
                            data = Enc_Utils.decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            GetCircleResponse circleResponse = gson.fromJson(data, GetCircleResponse.class);

                            if (circleResponse.getData() != null) {
                                mAction.setValue(new RechargeAction(RechargeAction.GET_CIRCLE_SUCCESS, circleResponse));
                            } else {
                                //String error=getAccountHolderResponse.getAccount().getError();
                                mAction.setValue(new RechargeAction(RechargeAction.GET_CIRCLE_ERROR, "error"));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException) {
                            mAction.setValue(new RechargeAction(RechargeAction.GET_CIRCLE_ERROR, "Timeout! Please try again later"));
                        } else if (e instanceof UnknownHostException) {
                            mAction.setValue(new RechargeAction(RechargeAction.GET_CIRCLE_ERROR, "No Internet"));
                        } else {
                            mAction.setValue(new RechargeAction(RechargeAction.GET_CIRCLE_ERROR, e.getMessage()));
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
                            String data = Enc_Utils.decValues(encr.revDecString(response.getData()));
                            data = Enc_Utils.decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            ValidateMpinResponse validateMpinResponse = gson.fromJson(data, ValidateMpinResponse.class);

                            if (validateMpinResponse.getValue() != null) {
                                mAction.setValue(new RechargeAction(RechargeAction.VALIDATE_MPIN_SUCCESS, validateMpinResponse));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException) {
                            mAction.setValue(new RechargeAction(RechargeAction.API_ERROR, "Timeout! Please try again later"));
                        } else if (e instanceof UnknownHostException) {
                            mAction.setValue(new RechargeAction(RechargeAction.API_ERROR, "No Internet"));
                        } else {
                            mAction.setValue(new RechargeAction(RechargeAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    public void recharge(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.recharge(body);
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
                            String data = Enc_Utils.decValues(encr.revDecString(response.getData()));
                            data = Enc_Utils.decValues(encr.revDecString(response.getData()));
                            Gson gson = new GsonBuilder().create();
                            RechargeResponse rechargeResponse = gson.fromJson(data, RechargeResponse.class);
                            if (rechargeResponse.getStatus().equals("1")) {
                                mAction.setValue(new RechargeAction(RechargeAction.RECHARGE_SUCCESS, rechargeResponse));
                            } else {
                                mAction.setValue(new RechargeAction(RechargeAction.RECHARGE_ERROR, rechargeResponse.getMsg()));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException) {
                            mAction.setValue(new RechargeAction(RechargeAction.API_ERROR, "Timeout! Please try again later"));
                        } else if (e instanceof UnknownHostException) {
                            mAction.setValue(new RechargeAction(RechargeAction.API_ERROR, "No Internet"));
                        } else {
                            mAction.setValue(new RechargeAction(RechargeAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
