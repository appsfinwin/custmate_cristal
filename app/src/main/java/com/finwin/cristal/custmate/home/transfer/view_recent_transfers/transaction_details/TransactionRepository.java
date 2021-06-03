package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.transaction_details;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_status.TransactionStatusResponse;
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

public class TransactionRepository {

    public static TransactionRepository instance;
    public static TransactionRepository getInstance()
    {
        if (instance==null)
        {
            instance=new TransactionRepository();
        }
        return instance;
    }

    MutableLiveData<TransactionDetailsAction> mAction;
    CompositeDisposable disposable;
    Enc_crypter encr = new Enc_crypter();

    public MutableLiveData<TransactionDetailsAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<TransactionDetailsAction> mAction) {
        this.mAction = mAction;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public void getTransactionStatus(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.getRecentTransactionStatus(body);
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
                            TransactionStatusResponse transactionStatusResponse = gson.fromJson(data, TransactionStatusResponse.class);

                            if (transactionStatusResponse.getBen()!=null)
                            {
                                mAction.setValue(new TransactionDetailsAction(TransactionDetailsAction.TRANSACTION_DETAILS,transactionStatusResponse));
                            }
                            else
                            {
//                                String error=transactionStatusResponse.getAccount().getError();
                                mAction.setValue(new TransactionDetailsAction(TransactionDetailsAction.API_ERROR,"Please Try again"));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new TransactionDetailsAction(TransactionDetailsAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new TransactionDetailsAction(TransactionDetailsAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new TransactionDetailsAction(TransactionDetailsAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
