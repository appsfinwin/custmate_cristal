package com.finwin.cristal.custmate.home.transfer.add_beneficiary;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.transfer.add_beneficiary.action.AddBeneficiaryAction;
import com.finwin.cristal.custmate.home.transfer.add_beneficiary.pojo.AddBeneficiaryResponse;
import com.finwin.cristal.custmate.home.transfer.add_beneficiary.pojo.GetIfscResponse;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.get_account_holder.GetAccountHolderResponse;
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

public class AddBeneficiaryRepository {

    public static AddBeneficiaryRepository instance;
    public static AddBeneficiaryRepository getInstance()
    {
        if (instance==null)
        {
            instance=new AddBeneficiaryRepository();
        }

        return instance;
    }

    Enc_crypter encr = new Enc_crypter();
    MutableLiveData<AddBeneficiaryAction> mAction;
    CompositeDisposable disposable;

    public MutableLiveData<AddBeneficiaryAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<AddBeneficiaryAction> mAction) {
        this.mAction = mAction;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }


    public void getIfsc(ApiInterface apiInterface, String ifsc) {
        Single<GetIfscResponse> call = apiInterface.getIfsc(ifsc);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetIfscResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(GetIfscResponse response) {

                            if (response!=null)
                            {
                                mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.GET_IFSC_SUCCESS,response));
                            }
                            else
                            {
                                String error=response.getError();
                                mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.GET_IFSC_ERROR,error));
                            }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.GET_IFSC_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.GET_IFSC_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.GET_IFSC_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    public void addBeneficiary(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.addBeneficiary(body);
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
                            AddBeneficiaryResponse addBeneficiaryResponse = gson.fromJson(data, AddBeneficiaryResponse.class);

                            if (addBeneficiaryResponse!=null)
                            {
                                mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.ADD_BENEFICIARY_SUCCESS,addBeneficiaryResponse));
                            }
                            else
                            {
                                String error=addBeneficiaryResponse.getError();
                                mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.API_ERROR, e.getMessage()));
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
                                mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.GET_ACCOUNT_HOLDER_SUCCESS,getAccountHolderResponse));
                            }
                            else
                            {
                                String error=getAccountHolderResponse.getAccount().getError();
                                mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.GET_ACCOUNT_HOLDER_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
