package com.finwin.cristal.custmate.home.transfer.add_beneficiary;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.databinding.FrgAddBeneficiaryBinding;
import com.finwin.cristal.custmate.home.transfer.add_beneficiary.action.AddBeneficiaryAction;
import com.finwin.cristal.custmate.home.transfer.add_beneficiary.pojo.GetIfscResponse;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AddBeneficiaryViewmodel extends AndroidViewModel implements Observable {
    public AddBeneficiaryViewmodel(@NonNull Application application) {
        super(application);

        this.application = application;
        repository = AddBeneficiaryRepository.getInstance();
        mAction = new MutableLiveData<>();
        disposable = new CompositeDisposable();

        repository.setDisposable(disposable);
        repository.setmAction(mAction);
        sharedPreferences= application.getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    AddBeneficiaryRepository repository;
    MutableLiveData<AddBeneficiaryAction> mAction;
    CompositeDisposable disposable;
    Enc_crypter encr = new Enc_crypter();
    ApiInterface apiInterface;
    Application application;
    FrgAddBeneficiaryBinding binding;
    private PropertyChangeRegistry registry = new PropertyChangeRegistry();

    public ObservableField<String> ben_type = new ObservableField<>("oa");
    public ObservableField<Boolean> cb_sameBank = new ObservableField<>(true);
    public ObservableField<Boolean> cb_otherBank = new ObservableField<>(false);
    public ObservableField<Integer> accountVerifyVisibility = new ObservableField<>(View.VISIBLE);
    public ObservableField<Integer> ifscVerifyVisibility = new ObservableField<>(GONE);
    public ObservableField<Integer> bankDetailsVisibility = new ObservableField<>(GONE);
    public ObservableField<String> obName = new ObservableField<>("");
    public ObservableField<String> obAccountNumber = new ObservableField<>("");
    public ObservableField<String> obReEnterAccountNumber = new ObservableField<>("");
    public ObservableField<String> obIfsc = new ObservableField<>("");
    public ObservableField<String> obBankName = new ObservableField<>("");
    public ObservableField<String> obBranchName = new ObservableField<>("");
    public ObservableField<String> obBankAddress = new ObservableField<>("");
    public ObservableField<String> obMobileNumber = new ObservableField<>("");

    public ObservableField<Boolean> isAccountVarified = new ObservableField<>(false);
    public ObservableField<Boolean> isIfscVarified = new ObservableField<>(false);


    public MutableLiveData<AddBeneficiaryAction> getmAction() {
        mAction = repository.getmAction();
        return mAction;
    }

    MutableLiveData<String> obBenType=new MutableLiveData<>();

    public MutableLiveData<String> getObBenType() {
        obBenType.setValue(ben_type.get());
        return obBenType;
    }

    public void clickSave(View view) {

        if (obName.get().equals("") ||
                obAccountNumber.get().equals("") ||
                obReEnterAccountNumber.get().equals("") ||
                !obAccountNumber.get().equals(obReEnterAccountNumber.get()) ||
                ben_type.get().equals("ob") && obIfsc.get().equals("") ||
                obMobileNumber.get().equals("")||
                (ben_type.get().equals("oa")) && (!isAccountVarified.get())||
                (ben_type.get().equals("ob")) && (!isIfscVarified.get() )
                )
        {

            if (obName.get().equals("") || obName.get().isEmpty()) {
                Toast.makeText(application, "Name  Cannot be Empty!", Toast.LENGTH_SHORT).show();
            }
            else if (obAccountNumber.get().equals("") || obAccountNumber.get().isEmpty()) {
                Toast.makeText(application, "Account Number Cannot be Empty!", Toast.LENGTH_SHORT).show();
            }
            else if (obReEnterAccountNumber.get().equals("") || obReEnterAccountNumber.get().isEmpty()) {
                Toast.makeText(application, "Please re-enter account number", Toast.LENGTH_SHORT).show();
            }
            else if (!obAccountNumber.get().equals(obReEnterAccountNumber.get())) {
                Toast.makeText(application, "Account Number Mismatch!", Toast.LENGTH_SHORT).show();
            }
            else if (ben_type.equals("ob") && obIfsc.get().equals("")) {
                Toast.makeText(application, "IFSC Cannot be empty!", Toast.LENGTH_SHORT).show();
            }
            else if (obMobileNumber.get().equals("") || obMobileNumber.get().isEmpty()) {
                Toast.makeText(application, "Mobile Number Cannot be empty!", Toast.LENGTH_SHORT).show();
            }
            else if ((ben_type.get().equals("oa")) && (!isAccountVarified.get())) {
                Toast.makeText(application, "Please verify Account Number", Toast.LENGTH_SHORT).show();
            }
            else if ((ben_type.get().equals("ob")) && (!isIfscVarified.get())) {
                Toast.makeText(application, "Please verify IFSC", Toast.LENGTH_SHORT).show();
            }
        }else {
            initLoading(view.getContext());
            addBeneficiary();
        }

    }

    public void clickBenList(View view)
    {
        mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.CLICK_BENEFICIARY_LIST));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getDisposable()).dispose();
        mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.DEFAULT));
        clearData();
    }

    public void radioChanged(RadioGroup radioGroup, int id) {
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(id);
        String radio = radioButton.getText().toString();
        switch (radio) {
            case "Same Bank":
                ben_type.set("oa");
                cb_sameBank.set(true);
                cb_otherBank.set(false);
                accountVerifyVisibility.set(View.VISIBLE);
                ifscVerifyVisibility.set(GONE);
                obBenType.setValue(ben_type.get());
                break;

            case "Other Bank":
                ben_type.set("ob");
                cb_sameBank.set(false);
                cb_otherBank.set(true);
                accountVerifyVisibility.set(GONE);
                ifscVerifyVisibility.set(VISIBLE);
                obBenType.setValue(ben_type.get());
                break;
        }
    }

    public void getIfsc(String ifsc) {

        apiInterface = RetrofitClient.RetrofitIfsc().create(ApiInterface.class);
        repository.getIfsc(apiInterface, obIfsc.get());
    }

    public void addBeneficiary() {
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("customer_id",
                sharedPreferences.getString(ConstantClass.custId,""));
        items.put("ben_name", obName.get());
        items.put("ben_mobile", obMobileNumber.get());
        items.put("ben_account_no", obAccountNumber.get());
        items.put("ben_ifscode", obIfsc.get());
        items.put("ben_type", ben_type.get());

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.addBeneficiary(apiInterface, body);
    }

    public void getAccountHolder() {
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("account_no", obAccountNumber.get());

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.getAccountHolder(apiInterface, body);
    }

    public void clickVerifyAccountNumber(View view) {
        if (obAccountNumber.get().equals("") || (obAccountNumber.get().isEmpty())) {
            Toast.makeText(application, "Please enter account number!", Toast.LENGTH_SHORT).show();
        } else {
            initLoading(view.getContext());
            getAccountHolder();
        }
    }

    public void clickVerifyIfsc(View view) {
        initLoading(view.getContext());
        getIfsc(obIfsc.get());
    }

    public void clickBack(View view)
    {
        mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.CLICK_BACK));
    }

    public void setBankData(GetIfscResponse getIfscResponse) {
        isIfscVarified.set(true);
        obBankName.set(getIfscResponse.getBANK());
        obBranchName.set(getIfscResponse.getBRANCH());
        obBankAddress.set(getIfscResponse.getADDRESS());
    }

    public void onTextChanged(CharSequence text) {
        // TODO do something with text

        binding.tvIfscVerify.setText("Verify");
        binding.imgIfscVerify.setImageResource(R.drawable.ic_verified_false);
        bankDetailsVisibility.set(GONE);
        isIfscVarified.set(false);
    }

    public void onAccountTextChanged(CharSequence text) {
        // TODO do something with text

        binding.tvVarify.setText("Verify");
        binding.imgVarify.setImageResource(R.drawable.ic_verified_false);
        isAccountVarified.set(false);
    }

    public void setBinding(FrgAddBeneficiaryBinding binding) {
        this.binding = binding;
    }


    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.remove(callback);
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

    public void clearData() {

        obName.set("");
        obAccountNumber.set("");
        obReEnterAccountNumber.set("");
        obIfsc.set("");
        obMobileNumber.set("");
        obBankName.set("");
        obBranchName.set("");
        obBankAddress.set("");
        isIfscVarified.set(false);
        isAccountVarified.set(false);


        binding.tvIfscVerify.setText("Verify");
        binding.imgIfscVerify.setImageResource(R.drawable.ic_verified_false);
        bankDetailsVisibility.set(GONE);
        isIfscVarified.set(false);

        binding.tvVarify.setText("Verify");
        binding.imgVarify.setImageResource(R.drawable.ic_verified_false);
        isAccountVarified.set(false);

        ben_type.set("oa");
        cb_sameBank.set(true);
        cb_otherBank.set(false);
        accountVerifyVisibility.set(View.VISIBLE);
        ifscVerifyVisibility.set(GONE);
        mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.DEFAULT));

    }
}


