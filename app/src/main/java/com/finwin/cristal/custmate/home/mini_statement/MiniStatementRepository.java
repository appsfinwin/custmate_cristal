package com.finwin.cristal.custmate.home.mini_statement;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.mini_statement.action.MiniStatementAction;
import com.finwin.cristal.custmate.home.mini_statement.pojo.MiniStatementResponse;
import com.finwin.cristal.custmate.pojo.Response;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

import static com.finwin.cristal.custmate.SupportingClass.Enc_Utils.decValues;

public class MiniStatementRepository {
    
    public static MiniStatementRepository instance;
    public static MiniStatementRepository getInstance()
    {
        if (instance==null)
        {
            instance=new MiniStatementRepository();
        }
        return instance;
    }

    CompositeDisposable compositeDisposable;
    MutableLiveData<MiniStatementAction> mAction;
    Enc_crypter encr = new Enc_crypter();

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<MiniStatementAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<MiniStatementAction> mAction) {
        this.mAction = mAction;
    }

    public void getMiniStatement(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.getMiniStatement(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response response) {

                        fetchData(response.getData());
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new MiniStatementAction(MiniStatementAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new MiniStatementAction(MiniStatementAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new MiniStatementAction(MiniStatementAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    Runnable mRunnable;
    Executor mExecutor = Executors.newSingleThreadExecutor();
    public void fetchData(final String response)
    {
        mRunnable = new Runnable() {
            @Override
            public void run() {


                try {
                    String data=decValues(encr.revDecString(response));
                    Gson gson = new GsonBuilder().create();
                    MiniStatementResponse miniStatementResponse = gson.fromJson(data, MiniStatementResponse.class);

                    if (miniStatementResponse.getMiniStatement()!=null)
                    {
                        mAction.postValue(new MiniStatementAction(MiniStatementAction.MINI_STATEMENT_SUCCESS,miniStatementResponse));
                    }
                    else
                    {
                        mAction.postValue(new MiniStatementAction(MiniStatementAction.API_ERROR,"Please try again!"));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mExecutor.execute(mRunnable);
    }
}
