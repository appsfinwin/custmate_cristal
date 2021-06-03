package com.finwin.cristal.custmate.home.reacharge;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.cristal.custmate.BR;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.reacharge.action.RechargeAction;
import com.finwin.cristal.custmate.home.reacharge.pojo.get_circle_response.CircleData;
import com.finwin.cristal.custmate.home.reacharge.pojo.get_operator_response.OperatorData;
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

public class RechargeViewmodel extends AndroidViewModel implements Observable {
    public RechargeViewmodel(@NonNull Application application) {
        super(application);
        this.application=application;
        repository = RechargeRepository.getInstance();
        disposable = new CompositeDisposable();
        mAction = new MutableLiveData<>();

        repository.setDisposable(disposable);
        repository.setmAction(mAction);
        initSpinner();
        sharedPreferences= application.getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Application application;
    private void initSpinner() {
        listCircle.clear();
        listOperator.clear();
        circleData.clear();
        operatorData.clear();

        listCircle.add("--Select Circle--");
        circleData.add(new CircleData("-1", "--Select Circle--", "-1"));

        listOperator.add("--Select Operator--");
        operatorData.add(new OperatorData("-1", "--Select Operator--", "", "-1"));
    }

    MutableLiveData<RechargeAction> mAction;
    CompositeDisposable disposable;
    Enc_crypter encr = new Enc_crypter();
    RechargeRepository repository;
    ApiInterface apiInterface;
    private PropertyChangeRegistry registry = new PropertyChangeRegistry();
    public int selectedCircle, selectedOperator;
    public ObservableField<String> operator = new ObservableField<>("-1");
    public ObservableField<String> circle = new ObservableField<>("-1");
    public ObservableField<String> mobileOrId = new ObservableField<>("");
    public ObservableField<String> amount = new ObservableField<>("");
    public ObservableField<String> operType = new ObservableField<>("");

    public ObservableArrayList<String> listCircle = new ObservableArrayList<>();
    public ObservableArrayList<CircleData> circleData = new ObservableArrayList<>();

    public ObservableArrayList<String> listOperator = new ObservableArrayList<>();
    public ObservableArrayList<OperatorData> operatorData = new ObservableArrayList<>();

    @Bindable
    public int getSelectedCircle() {
        return selectedCircle;
    }

    public void setSelectedCircle(int selectedCircle) {
        this.selectedCircle = selectedCircle;
        registry.notifyChange(this, BR.selectedCircle);
    }

    @Bindable
    public int getSelectedOperator() {
        return selectedOperator;
    }

    public void setSelectedOperator(int selectedOperator) {
        this.selectedOperator = selectedOperator;
        registry.notifyChange(this, BR.selectedOperator);
    }

    public void onSelectedOperator(AdapterView<?> parent, View view, int position, long id) {
        operator.set(operatorData.get(position).getOprId());

    }

    public void onSelectedCircle(AdapterView<?> parent, View view, int position, long id) {
        circle.set(circleData.get(position).getCirId());
    }

    public MutableLiveData<RechargeAction> getmAction() {
        mAction = repository.getmAction();
        return mAction;
    }

    public void getOperatorList(String type) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("type", type);

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.getOperatorList(apiInterface, body);
    }

    public void getCircle() {
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("account_no",
                sharedPreferences.getString(ConstantClass.accountNumber,""));

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.getCircle(apiInterface, body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        (repository.getDisposable()).dispose();
        mAction.setValue(new RechargeAction(RechargeAction.DEFAULT));
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.remove(callback);
    }

    public void setCircleData(List<CircleData> data) {
        for (CircleData circleData1 : data) {
            listCircle.add(circleData1.getCirName());
            circleData.add(circleData1);
        }
    }

    public void validateMpin(Map<String, String> params) {

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.validateMpin(apiInterface, body);
    }

    public void recharge() {

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();

        items.put("account_no", sharedPreferences.getString(ConstantClass.accountNumber,""));
        items.put("agent_id", "0");
        items.put("amount", amount.get());
        items.put("circle", circle.get());
        items.put("customer_id",
                sharedPreferences.getString(ConstantClass.custId,""));
        items.put("mobile", mobileOrId.get());
        items.put("Operator", operator.get());
        items.put("recharge_type", operType.get());

        Log.e("getRechargeMobile items", String.valueOf(items));

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        repository.recharge(apiInterface, body);
    }

    public void setOperatorData(List<OperatorData> data) {

        for (OperatorData data1 : data) {
            listOperator.add(data1.getOprName());
            operatorData.add(data1);
        }
    }

    public void clickBack(View view) {
        mAction.setValue(new RechargeAction(RechargeAction.CLICK_BACK));
    }

    public void clickProceed(View view) {
        if ((amount.get().equals("")) || (mobileOrId.get().equals("")) || (circle.get().equals("-1")) || (operator.get().equals("-1"))) {
            if (operator.get().equals("-1")) {
                Snackbar.make(view, "Please select an Operator", Snackbar.LENGTH_LONG).show();
            } else if (circle.get().equals("-1")) {
                Snackbar.make(view, "Please select an Circle", Snackbar.LENGTH_LONG).show();
            } else if (mobileOrId.get().equals("")) {
                Snackbar.make(view, "Mobile number or Id cannot be empty!", Snackbar.LENGTH_LONG).show();
            } else if (amount.get().equals("")) {
                Snackbar.make(view, "Amount cannot be empty!", Snackbar.LENGTH_LONG).show();
            }
        }else
        {

            Toast.makeText(application, "Recharge not available right now!", Toast.LENGTH_SHORT).show();

            //mAction.setValue(new RechargeAction(RechargeAction.CLICK_PROCEED));
        }

    }
}
