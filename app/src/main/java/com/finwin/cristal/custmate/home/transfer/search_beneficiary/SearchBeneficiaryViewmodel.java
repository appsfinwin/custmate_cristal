package com.finwin.cristal.custmate.home.transfer.search_beneficiary;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SearchBeneficiaryViewmodel extends AndroidViewModel {
    public SearchBeneficiaryViewmodel(@NonNull Application application) {
        super(application);

        mAction=new MutableLiveData<>();
        disposable=new CompositeDisposable();

        repository=SearchBeneficiaryRepository.getInstance();
        repository.setDisposable(disposable);
        repository.setmAction(mAction);
        sharedPreferences=application.getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    MutableLiveData<SearchBeneficairyAction> mAction;
    CompositeDisposable disposable;
    SearchBeneficiaryRepository repository;
    ApiInterface apiInterface;
    static Enc_crypter encr = new Enc_crypter();

    public MutableLiveData<SearchBeneficairyAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public void getBeneficiary(String benType) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("customer_id", sharedPreferences.getString(ConstantClass.custId,""));
        items.put("ben_name", "");
        items.put("ben_mobile", "");
        items.put("ben_account_no", "");
        items.put("ben_ifscode", "");
        items.put("ben_type", benType);

        Log.e("getBeneficiary: ", String.valueOf(items));
        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.getBeneficiary(apiInterface, body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getDisposable()).dispose();
        mAction.setValue(new SearchBeneficairyAction(SearchBeneficairyAction.DEFAULT));

    }
}
