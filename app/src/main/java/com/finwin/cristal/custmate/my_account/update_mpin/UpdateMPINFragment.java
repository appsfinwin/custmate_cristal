package com.finwin.cristal.custmate.my_account.update_mpin;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;

import com.finwin.cristal.custmate.databinding.FragmentUpdateMpinBinding;
import com.finwin.cristal.custmate.my_account.update_mpin.action.UpdateMpinAction;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class UpdateMPINFragment extends Fragment {
    Context context;
    RequestQueue requestQueue;
    EditText et_old_pin, et_new_mpin, et_confirm_new_min;
    Button update;
    final Enc_crypter encr = new Enc_crypter();
    String demessage;
    ImageButton iBtn_back;
    FragmentUpdateMpinBinding binding;
    UpdateMpinViewmodel viewmodel;
    Dialog dialogMpin;
    SweetAlertDialog loading,alertSuccess;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences= getActivity().getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_mpin, container, false);
        viewmodel = new ViewModelProvider(this).get(UpdateMpinViewmodel.class);
        binding.setViewmodel(viewmodel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        requestQueue = Volley.newRequestQueue(getActivity());
        et_old_pin = binding.etMpinOld;
        et_new_mpin = binding.etMpinNew;
        et_confirm_new_min = binding.etMpinNewConfirm;
        update = binding.btnMpinUpdate;

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_new_mpin.getText().toString().equals(et_confirm_new_min.getText().toString())) {
                    if (et_new_mpin.getText().toString().length() == 6) {
                        dialogMpin = new Dialog(context);
                        dialogMpin.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialogMpin.setCancelable(true);
                        dialogMpin.setContentView(R.layout.validation);
                        final Button validate = dialogMpin.findViewById(R.id.btn_validate);
                        final EditText username = dialogMpin.findViewById(R.id.et_username);
                        final EditText password = dialogMpin.findViewById(R.id.et_password);
                        validate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (username.getText().toString().equals("") && password.getText().toString().equals("")) {
                                    Toast.makeText(getActivity(), "Username & Password Must not be Null", Toast.LENGTH_LONG).show();
                                } else {
                                    validateUser(username.getText().toString(), password.getText().toString());
                                }
                            }
                        });
                        dialogMpin.show();
                    } else {
                        Toast.makeText(getActivity(), "MPIN Should Be 6 Digits", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "MPIN do not match!", Toast.LENGTH_LONG).show();
                }
            }
        });


        iBtn_back = view.findViewById(R.id.ibtn_back);
        iBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        viewmodel.getmAction().observe(getViewLifecycleOwner(), new Observer<UpdateMpinAction>() {
            @Override
            public void onChanged(UpdateMpinAction updateMpinAction) {
                switch (updateMpinAction.getAction()) {
                    case UpdateMpinAction.LOGIN_SUCCESS:
                        dialogMpin.dismiss();
                        validateMPIN();

                        break;
                    case UpdateMpinAction.API_ERROR:
                        Toast.makeText(context, updateMpinAction.getError(), Toast.LENGTH_SHORT).show();
                        break;

                    case UpdateMpinAction.MPIN_SUCCESS:
                        loading.dismissWithAnimation();
                        alertSuccess=new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                                alertSuccess.setTitleText("SUCCESS")
                                .setContentText("MPIN Updated")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        assert getFragmentManager() != null;
                                        getFragmentManager().popBackStack();
                                       // viewmodel.getmAction().setValue(new UpdateMpinAction(UpdateMpinAction.DEFAULT));
                                        alertSuccess.dismiss();

                                    }
                                })
                                .show();
                        break;

                    case UpdateMpinAction.MPIN_ERROR:
                        loading.dismissWithAnimation();
                        break;
                        case UpdateMpinAction.DEFAULT:
                            break;

                }
            }
        });
    }

    public void validateUser(String username, String password) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("username", username);
        items.put("password", password);

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
        viewmodel.validateUser(params);

    }

    public void validateMPIN() {

        loading = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        loading.setTitleText("Please wait")
                .show();

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("userid",
                sharedPreferences.getString(ConstantClass.custId,""));
        items.put("oldMPIN", et_old_pin.getText().toString());
        items.put("newMPIN", et_confirm_new_min.getText().toString());

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
        viewmodel.validateMpin(params);

    }


}
