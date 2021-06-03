package com.finwin.cristal.custmate.home.transfer_main;

import androidx.lifecycle.MutableLiveData;


import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.transfer_main.action.TransferAction;

import io.reactivex.disposables.CompositeDisposable;


public class TransferRepository {
    public static TransferRepository instance;
    public static TransferRepository getInstance()
    {
        if (instance==null)
        {
            instance=new TransferRepository();
        }
        return instance;
    }
    final Enc_crypter encr = new Enc_crypter();

    MutableLiveData<TransferAction> mAction;
    CompositeDisposable disposable;

    public void setmAction(MutableLiveData<TransferAction> mAction) {
        this.mAction = mAction;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

//    public void getTransactionList(ApiInterface apiInterface, RequestBody body) {
//        Single<Response> call = apiInterface.getRecentTransactionList(body);
//        call.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<Response>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposable.add(d);
//                    }
//
//                    @Override
//                    public void onSuccess(Response response) {
//
//                        try {
//                            //String data=decVaues(encr.revDecString(response.getData()));
//                            String data=decValues(encr.revDecString(response.getData()));
//                            Gson gson = new GsonBuilder().create();
//                            TransactionListResponse transactionListResponse = gson.fromJson(data, TransactionListResponse.class);
//
//                            if (transactionListResponse.getBen().getData().size()>0)
//                            {
//                                mAction.setValue(new TransferAction(TransferAction.TRANSACTIONLIST_SUCCESS,transactionListResponse));
//                            }
//                            else
//                            {
//                               // mAction.setValue(new TransferAction(TransferAction.TRANSACTIONLIST_EMPTY));
//                            }
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        if (e instanceof SocketTimeoutException)
//                        {
//                            //mAction.setValue(new TransferAction(TransferAction.API_ERROR,"Timeout! Please try again later"));
//                        }else if (e instanceof UnknownHostException)
//                        {
//                           // mAction.setValue(new TransferAction(TransferAction.API_ERROR,"No Internet"));
//                        }else {
//                           // mAction.setValue(new TransferAction(TransferAction.API_ERROR, e.getMessage()));
//                        }
//                    }
//                });
//    }
}
