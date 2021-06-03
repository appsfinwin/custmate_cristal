package com.finwin.cristal.custmate.home.transfer.search_beneficiary;

import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
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

public class SearchBeneficiaryRepository {

    public static SearchBeneficiaryRepository instance;
    public static SearchBeneficiaryRepository getInstance()
    {
        if (instance==null)
        {
            instance=new SearchBeneficiaryRepository();
        }
        return instance;
    }
    MutableLiveData<SearchBeneficairyAction> mAction;
    CompositeDisposable disposable;
    Enc_crypter encr = new Enc_crypter();

    public MutableLiveData<SearchBeneficairyAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<SearchBeneficairyAction> mAction) {
        this.mAction = mAction;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public void getBeneficiary(ApiInterface apiInterface, RequestBody body) {
        Single<Response> call = apiInterface.getBeneficiary(body);
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
                            BeneficiaryListResponse beneficiaryListResponse = gson.fromJson(data, BeneficiaryListResponse.class);

                            if (beneficiaryListResponse.getData()!=null)
                            {
                                mAction.setValue(new SearchBeneficairyAction(SearchBeneficairyAction.BENEFICIARY_SUCCESS,beneficiaryListResponse));
                            }
                            else
                            {
                                String error=beneficiaryListResponse.getError();
                                mAction.setValue(new SearchBeneficairyAction(SearchBeneficairyAction.API_ERROR,error));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new SearchBeneficairyAction(SearchBeneficairyAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new SearchBeneficairyAction(SearchBeneficairyAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new SearchBeneficairyAction(SearchBeneficairyAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
