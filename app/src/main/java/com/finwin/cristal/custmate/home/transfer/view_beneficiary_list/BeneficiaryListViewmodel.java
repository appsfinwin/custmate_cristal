package com.finwin.cristal.custmate.home.transfer.view_beneficiary_list;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.action.BeneficiaryListAction;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.retrofit.RetrofitClient;
import com.finwin.cristal.custmate.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BeneficiaryListViewmodel extends AndroidViewModel {

    Enc_crypter encr = new Enc_crypter();
    ApiInterface apiInterface;
    BeneficiaryListRepository repository;
    MutableLiveData<BeneficiaryListAction> mAction;
    CompositeDisposable compositeDisposable;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public MutableLiveData<BeneficiaryListAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public BeneficiaryListViewmodel(@NonNull Application application) {
        super(application);
        repository=BeneficiaryListRepository.getInstance();
        mAction=new MutableLiveData<>();
        compositeDisposable=new CompositeDisposable();

        repository.setDisposable(compositeDisposable);
        repository.setmAction(mAction);
        sharedPreferences= application.getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
    }

    public void getBeneficiary(String benType) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("customer_id",
                sharedPreferences.getString(ConstantClass.custId,""));
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
        mAction.setValue(new BeneficiaryListAction(BeneficiaryListAction.DEFAULT));
    }

    SweetAlertDialog loading;

    public void initLoading(Context context) {
        loading = Services.showProgressDialog(context);
    }

    public void cancelLoading() {
        if (loading != null) {
            loading.cancel();
            loading = null;
        }
    }

    public void clickBack(View view)
    {
        mAction.setValue(new BeneficiaryListAction(BeneficiaryListAction.CLICK_BACK));
    }
}
