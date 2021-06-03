package com.finwin.cristal.custmate.home.balance_enquiry;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.balance_enquiry.action.BalanceAction;
import com.finwin.cristal.custmate.home.balance_enquiry.pojo.BalanceResponse;
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

public class BalanceRepository {
    public static BalanceRepository instance;
    public static BalanceRepository getInstance()
    {
        if (instance==null)
        {
            instance=new BalanceRepository();
        }
        return instance;
    }

    BalanceRepository repository;
    CompositeDisposable compositeDisposable;
    MutableLiveData<BalanceAction> mAction;
    Enc_crypter encr = new Enc_crypter();

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<BalanceAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<BalanceAction> mAction) {
        this.mAction = mAction;
    }

    public void balanceEnquiry(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.balanceEnqury(body);
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
                            BalanceResponse balanceResponse = gson.fromJson(data, BalanceResponse.class);

                            if (balanceResponse.getBalance()!=null)
                            {
                                mAction.setValue(new BalanceAction(BalanceAction.GET_BALANCE_SUCCESS,balanceResponse));
                            }
                            else
                            {
                                mAction.setValue(new BalanceAction(BalanceAction.API_ERROR,"Please try again!"));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new BalanceAction(BalanceAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new BalanceAction(BalanceAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new BalanceAction(BalanceAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
