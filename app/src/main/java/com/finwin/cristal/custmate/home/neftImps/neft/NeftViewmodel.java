package com.finwin.cristal.custmate.home.neftImps.neft;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.finwin.cristal.custmate.BR;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.databinding.FrgFundTransOneBinding;
import com.finwin.cristal.custmate.home.neftImps.pojo.neft_transfer.model.ModelTransferType;
import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryData;
import com.finwin.cristal.custmate.retrofit.ApiInterface;
import com.finwin.cristal.custmate.retrofit.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NeftViewmodel extends AndroidViewModel implements Observable {
    public NeftViewmodel(@NonNull Application application) {
        super(application);
        this.application=application;
        mAction=new MutableLiveData<>();
        repository=NeftRepository.getInstance();
        disposable=new CompositeDisposable();

        repository.setCompositeDisposable(disposable);
        repository.setmAction(mAction);
        initSpinnerData();

    }

    private void setSpinnserAdapter() {
        binding.spinnerTransferType.setAdapter(new ArrayAdapter<>(application.getApplicationContext(),
                R.layout.spinner_list_item));
    }

    private void initSpinnerData() {
        beneficiaryList.clear();
        beneficiaryListData.clear();
        transferTypeList.clear();
        transferTypeListData.clear();

        beneficiaryList.add("--Select Beneficiary--");
        beneficiaryListData.add(new BeneficiaryData("-1","--Select Beneficiary--","",""));

        transferTypeListData.add(new ModelTransferType("--Select Transfer Type--","-1"));
        transferTypeListData.add(new ModelTransferType("RTGS","RT"));
        transferTypeListData.add(new ModelTransferType("NEFT","NE"));
        transferTypeListData.add(new ModelTransferType("IMPS","PA"));
        transferTypeListData.add(new ModelTransferType("Fund Transfer(AXIS bank only)","FT"));

        transferTypeList.add("--Select Transfer Type--");
        transferTypeList.add("RTGS");
        transferTypeList.add("NEFT");
        transferTypeList.add("IMPS");
        transferTypeList.add("Fund Transfer(AXIS bank only)");

    }

    Application application;
    ApiInterface apiInterface;
    MutableLiveData<NeftAction> mAction;
    NeftRepository repository;
    CompositeDisposable disposable;
    int selectedBeneficiary,selectedTransferType;
    private PropertyChangeRegistry registry = new PropertyChangeRegistry();

    public ObservableArrayList<String> beneficiaryList=new ObservableArrayList<>();
    public ObservableArrayList<BeneficiaryData> beneficiaryListData=new ObservableArrayList<>();
    public ObservableArrayList<String> transferTypeList=new ObservableArrayList<>();
    public ObservableArrayList<ModelTransferType> transferTypeListData=new ObservableArrayList<>();

    public ObservableField<String> transferType_id=new ObservableField<>("-1");
    public ObservableField<String> beneficiary_account=new ObservableField<>("");
    public ObservableField<String> beneficiary_id=new ObservableField<>("-1");
    public ObservableField<String> transferAmount=new ObservableField<>("");


    public MutableLiveData<NeftAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }
    @Bindable
    public int getSelectedBeneficiary() {
        return selectedBeneficiary;
    }

    public void setSelectedBeneficiary(int selectedBeneficiary) {
        this.selectedBeneficiary = selectedBeneficiary;
        registry.notifyChange(this, BR.selectedBeneficiary);
    }
    @Bindable
    public int getSelectedTransferType() {
        return selectedTransferType;
    }

    public void setSelectedTransferType(int selectedTransferType) {
        this.selectedTransferType = selectedTransferType;
        registry.notifyChange(this, BR.selectedTransferType);
    }



    public void validateMpin(Map<String, String> params) {

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.validateMpin(apiInterface, body);
    }

    public void generateOTP(Map<String, String> params) {

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.generateOtp(apiInterface, body);
    }
    public void onSelectedBeneficiary(AdapterView<?> parent, View view, int position, long id)
    {

        if (position!=0) {
            beneficiary_account.set(beneficiaryListData.get(position).getReceiverAccountno());
            beneficiary_id.set(beneficiaryListData.get(position).getReceiverid());

            binding.layoutAccountDetails.setVisibility(View.VISIBLE);
            binding.tvAccountNumber.setText(beneficiaryListData.get(position).getReceiverAccountno());
            binding.tvIfsc.setText(beneficiaryListData.get(position).getReceiverIfsccode());
            binding.edtAmount.setEnabled(true);
        }else {
            binding.layoutAccountDetails.setVisibility(View.GONE);
        }
    }

    public void onSelectedTransferType(AdapterView<?> parent, View view, int position, long id)
    {
        transferType_id.set(transferTypeListData.get(position).getTransferId());
    }


    public void loginApi()
    {
        Map<String, Object> jsonParams = new HashMap<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.generateOtp(apiInterface,body);
    }

    public void getBeneficiary(Map<String, String> params) {

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.getBeneficiary(apiInterface, body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getCompositeDisposable()).dispose();
        mAction.setValue(new NeftAction(NeftAction.DEFAULT));
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.remove(callback);

    }

    public void setBeneficiaryList(List<BeneficiaryData> data) {

        beneficiaryList.clear();
        beneficiaryListData.clear();
        beneficiaryList.add("--Select Beneficiary--");
        beneficiaryListData.add(new BeneficiaryData("-1","--Select Beneficiary--","",""));

        for (BeneficiaryData beneficiaryData: data)
        {
            beneficiaryList.add(beneficiaryData.getReceiverName());
            beneficiaryListData.add(beneficiaryData);
        }

    }
    FrgFundTransOneBinding binding;
    public void setBinding(FrgFundTransOneBinding binding) {
        this.binding=binding;
        setSpinnserAdapter();
    }

    public void clickProceed(View view)
    {
        if ((!beneficiary_id.get().equals("-1")) && (!transferType_id.get().equals("-1")) && (!transferAmount.get().equals("")))
        {
            mAction.setValue(new NeftAction(NeftAction.CLICK_PROCEED));

        }else {
            if (beneficiary_id.get().equals("-1"))
            {
                Snackbar.make(view, "Please select beneficiary", Snackbar.LENGTH_LONG).show();
            }else if (transferType_id.get().equals("-1"))
            {
                Snackbar.make(view, "Please select transfer type", Snackbar.LENGTH_LONG).show();
            }else if (transferAmount.get().equals(""))
            {
                Snackbar.make(view, "Transfer amount cannot be empty!", Snackbar.LENGTH_LONG).show();
            }

        }
    }
}
