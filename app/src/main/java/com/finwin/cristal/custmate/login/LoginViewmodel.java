package com.finwin.cristal.custmate.login;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.databinding.ActivityLoginBinding;
import com.finwin.cristal.custmate.login.action.LoginAction;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.retrofit.RetrofitClient;
import com.finwin.cristal.custmate.utils.Services;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginViewmodel extends AndroidViewModel {
    public LoginViewmodel(@NonNull Application application) {
        super(application);

        repository=LoginRepository.getInstance();
        mAction=new MutableLiveData<>();
        disposable=new CompositeDisposable();

        repository.setDisposable(disposable);
        repository.setmAction(mAction);

    }

    CompositeDisposable disposable;
    MutableLiveData<LoginAction> mAction;
    LoginRepository repository;
    ApiInterface apiInterface;
    final Enc_crypter encr = new Enc_crypter();
    ActivityLoginBinding binding;

    public ObservableField<String> ob_userName=new ObservableField<>("");
    public ObservableField<String> ob_password=new ObservableField<>("");

    public MutableLiveData<LoginAction> getmAction()
    {
        mAction=repository.getmAction();
        return mAction;
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
    @Override
    protected void onCleared() {
        super.onCleared();

        (repository.getDisposable()).dispose();
        mAction.setValue(new LoginAction(LoginAction.DEFAULT));
    }

    public void getApiKey() {
        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getApiKey(apiInterface);
    }


    public void login() {

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("username", ob_userName.get());
        items.put("password", ob_password.get());

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));


        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.login(apiInterface, body);
    }

    public void clickLogin(View view)
    {
        if (ob_userName.get().equals(""))
        {
            Snackbar.make(view, "Username cannot be empty!", Snackbar.LENGTH_LONG).show();
        }else if (ob_password.get().equals(""))
        {
            Snackbar.make(view, "Password cannot be empty!", Snackbar.LENGTH_LONG).show();
        }else {
           initLoading(view.getContext());
            login();
        }
    }



    public void clickSignUp(View view)
    {
        mAction.setValue(new LoginAction(LoginAction.CLICK_SIGNUP));
    }

    public void setBinding(ActivityLoginBinding binding) {
        this.binding=binding;
    }
}
