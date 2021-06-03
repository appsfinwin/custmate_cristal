package com.finwin.cristal.custmate.home.transfer.fund_transfer_account;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.databinding.FrgFundTransferAccBinding;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FundTransferAccountViewmodel extends AndroidViewModel {

    public FundTransferAccountViewmodel(@NonNull Application application) {
        super(application);

        this.application = application;
        mAction = new MutableLiveData<>();
        disposable = new CompositeDisposable();
        repository = FundTransferAccountRepository.getInstance();

        repository.setDisposable(disposable);
        repository.setmAction(mAction);

        sharedPreferences = application.getSharedPreferences("com.finwin.cristal.custmate", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    FrgFundTransferAccBinding binding;
    FundTransferAccountRepository repository;
    MutableLiveData<FundTransferAccountAction> mAction;
    CompositeDisposable disposable;
    ApiInterface apiInterface;
    Application application;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public ObservableField<String> varified = new ObservableField<>("Account not verified");
    public ObservableField<String> tvAccountNumber = new ObservableField<>("");
    public ObservableField<String> etAccountNumber = new ObservableField<>("");
    public ObservableField<String> etAmount = new ObservableField<>("");
    public ObservableField<String> tvName = new ObservableField<>("");
    public ObservableField<String> tvMobile = new ObservableField<>("");
    public ObservableField<Boolean> isAcccountVerified = new ObservableField<>(false);
    public ObservableField<Double> currentBalance= new ObservableField<>(0.0);
    public MutableLiveData<FundTransferAccountAction> getmAction() {
        mAction = repository.getmAction();
        return mAction;
    }


    @Override
    protected void onCleared() {
        super.onCleared();

        (repository.getDisposable()).dispose();
        mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.DEFAULT));
    }

    public void onTextChanged(CharSequence text) {
        // TODO do something with text
        binding.btnVerify.setText("Click here to verify Account");
        binding.btnVerify.setTextColor(application.getResources().getColor(R.color.grey));
        binding.btnVerify.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_verified_false, 0);
        isAcccountVerified.set(false);

    }

    public void generateOtp() {

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("particular", "mob");
        items.put("account_no", sharedPreferences.getString(ConstantClass.accountNumber, ""));
        items.put("amount", etAmount.get());
        items.put("agent_id",
                sharedPreferences.getString(ConstantClass.custId, ""));
        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.generateOtp(apiInterface, body);
    }

    public void validateMpin(Map<String, String> params) {
        Map<String, Object> jsonParams = new HashMap<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.validateMpin(apiInterface, body);
    }

    public void getAccountHolder() {
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("account_no",
                sharedPreferences.getString(ConstantClass.accountNumber, ""));

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getAccountHolder(apiInterface, body);
    }

    Enc_crypter encr = new Enc_crypter();

    public void getCreditAccountHolder(String accountNumber) {

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("account_no", accountNumber);

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getCreditAccountHolder(apiInterface, body);
    }


    public void clickVerifyAccount(View view) {
        if (etAccountNumber.get().equals("")) {
            Toast.makeText(view.getContext(), "Please Enter Credit Account Number", Toast.LENGTH_LONG).show();
        } else {
            getCreditAccountHolder(etAccountNumber.get());
        }
    }

    public void clickSearchBeneficiary(View view) {
        mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.CLICK_SEARCH_BENEFICIARY));
    }

    public void clickProceed(View view) {
        if (!isAcccountVerified.get()) {
            Toast.makeText(view.getContext(), "Please Verify Account Number", Toast.LENGTH_LONG).show();
        } else if (etAmount.get().equals("")) {
            Toast.makeText(view.getContext(), "Please Enter Amount", Toast.LENGTH_LONG).show();
        } else if ((!sharedPreferences.getString(ConstantClass.SCHEME,"").equals("SB"))) {
            Toast.makeText(view.getContext(), "Please select a savings account", Toast.LENGTH_SHORT).show();
        } else
        if (Double.parseDouble(etAmount.get())>currentBalance.get()) {
            Toast.makeText(view.getContext(), "No sufficient balance!", Toast.LENGTH_SHORT).show();
        }

        else {
            mAction.setValue(new FundTransferAccountAction(FundTransferAccountAction.CLICK_PROCEED));
        }
    }

    public void setBinding(FrgFundTransferAccBinding binding) {
        this.binding = binding;
    }

    public void reset() {
        etAmount.set("");
        etAccountNumber.set("");
        isAcccountVerified.set(false);
        editor.putString("success", "no");
    }
}
