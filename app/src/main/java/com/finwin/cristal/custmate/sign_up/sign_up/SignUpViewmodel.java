package com.finwin.cristal.custmate.sign_up.sign_up;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.retrofit.RetrofitClient;
import com.finwin.cristal.custmate.sign_up.sign_up.action.SignupAction;
import com.finwin.cristal.custmate.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SignUpViewmodel extends AndroidViewModel {
    public SignUpViewmodel(@NonNull Application application) {
        super(application);
        this.application=application;
        repository= SignupRepository.getInstance();

        disposable=new CompositeDisposable();
        mAction=new MutableLiveData<>();

        repository.setDisposable(disposable);
        repository.setmAction(mAction);
        sharedPreferences=application.getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ObservableField<String> obAccountNumber=new ObservableField<>("");
    public ObservableField<String> obName=new ObservableField<>("");
    public ObservableField<String> obMobile=new ObservableField<>("");
    public ObservableField<String> obPassword=new ObservableField<>("");
    public ObservableField<String> obConfirmPassword=new ObservableField<>("");
    Application application;
    SignupRepository repository;
    ApiInterface apiInterface;
    Enc_crypter encr = new Enc_crypter();

    CompositeDisposable disposable;
    MutableLiveData<SignupAction> mAction;



    public MutableLiveData<SignupAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public void clickSignUp(View view)
    {
        {
            if (obAccountNumber.get().equals(""))
            {
                toast(view.getContext(),"Account number cannot be empty");
            }else if (obName.get().equals(""))
            {
                toast(view.getContext(),"Name cannot be empty");
            }else if (obMobile.get().equals(""))
            {
                toast(view.getContext(),"Phone number cannot be empty");
            }else if (obPassword.get().equals(""))
            {
                toast(view.getContext(),"Please enter password!");
            }else if (obConfirmPassword.get().equals(""))
            {
                toast(view.getContext(),"Please confirm password");
            }else if (!obPassword.get().equals(obConfirmPassword.get()))
            {
                toast(view.getContext(),"Passwords do not match");
            }
            else {
                editor.putString(ConstantClass.accountNumber, obAccountNumber.get());
                editor.putString(ConstantClass.userName, obName.get());
                editor.putString(ConstantClass.phoneNumber, obMobile.get());
                editor.putString(ConstantClass.const_password, obPassword.get());
                editor.commit();

                initLoading(view.getContext());
                signUp();
            }
        }
    }

    private void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private void signUp() {



        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("account_no", obAccountNumber.get());

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        String request=(new JSONObject(items)).toString();
        String data = encr.conRevString(Enc_Utils.enValues(items));
        params.put("data", data);

            Map<String, Object> jsonParams = new HashMap<>();

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());

            apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
            repository.getAccountHolder(apiInterface,body);
        }
    public void generateOtp() {

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("particular", "CUSTMATE_REG");
        items.put("account_no", obAccountNumber.get());
        items.put("amount", "0");
        items.put("agent_id", "0");
        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));


        Map<String, Object> jsonParams = new HashMap<>();
        String request=(new JSONObject(params)).toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.generateOtp(apiInterface, body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getDisposable()).dispose();
        mAction.setValue(new SignupAction(SignupAction.DEFAULT));
    }

    public void getApiKey() {
        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getApiKey(apiInterface);
    }

    public void clickSignIn(View view)
    {
        mAction.setValue(new SignupAction(SignupAction.CLICK_SIGN_IN));
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


}

