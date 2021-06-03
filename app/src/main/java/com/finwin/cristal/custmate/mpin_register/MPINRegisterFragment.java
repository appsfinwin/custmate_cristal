package com.finwin.cristal.custmate.mpin_register;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.activity_main.ActivityMain;
import com.finwin.cristal.custmate.home.home_fragment.HomeFragment;
import com.finwin.cristal.custmate.mpin_register.action.MpinRegisterAction;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MPINRegisterFragment extends Fragment {

    Button submit, finish;

    AppCompatEditText mpin1, mpin2;
    Context context;
    RequestQueue requestQueue;
    final Enc_crypter encr = new Enc_crypter();
    String demessage;
    Dialog dialogMpin;
    MpinRegisterViewmodel viewmodel;
    SweetAlertDialog sweetAlertDialog,mpinSuccess;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        requestQueue = Volley.newRequestQueue(context);
        sharedPreferences= getActivity().getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewmodel=new ViewModelProvider(getActivity()).get(MpinRegisterViewmodel.class);
        return inflater.inflate(R.layout.fragment_mpinregister, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mpin1 = view.findViewById(R.id.et_mpin_1);
        mpin2 = view.findViewById(R.id.et_mpin_2);
        submit = view.findViewById(R.id.btn_mpin_submit);
        finish = view.findViewById(R.id.btn_mpin_finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
               // assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment containerFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.frame_layout, containerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        viewmodel.getmAction().observe(getViewLifecycleOwner(), new Observer<MpinRegisterAction>() {
            @Override
            public void onChanged(MpinRegisterAction mpinRegisterAction) {
                switch (mpinRegisterAction.getAction())
                {
                    case MpinRegisterAction.LOGIN_SUCCESS:

                        dialogMpin.dismiss();
                        registerMpin();
                        break;

                        case MpinRegisterAction.MPIN_REGISTER_SUCCESS:

                            sweetAlertDialog.dismiss();
                            mpinSuccess=new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                            mpinSuccess.setTitleText("SUCCESS");
                            mpinSuccess.setContentText("MPIN Registered")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            Intent intent= new Intent(getActivity(), ActivityMain.class);
                                            getActivity().finish();
                                            startActivity(intent);
                                            mpinSuccess.dismiss();

                                        }
                                    });
                            mpinSuccess.show();
                            mpin1.setEnabled(false);
                            mpin2.setEnabled(false);
                            submit.setEnabled(false);
                            break;

                            case MpinRegisterAction.API_ERROR:

                    case MpinRegisterAction.MPIN_REGISTER_ERROR:

                        sweetAlertDialog.dismiss();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ERROR")
                                        .setContentText(mpinRegisterAction.getError())
                                        .show();
                                break;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mpin1.getText().toString().equals(mpin2.getText().toString())) {
                    if (mpin1.getText().toString().length() == 6) {
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

        super.onViewCreated(view, savedInstanceState);
    }

    public void validateUser(String username, String password) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("username", username);
        items.put("password", password);

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
        viewmodel.validateUser(params);

    }

    public void registerMpin() {

        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Please wait")
                .show();
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("userid", sharedPreferences.getString(ConstantClass.custId,""));
        items.put("MPIN", mpin2.getText().toString());



        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
        viewmodel.registerMpin(params);
    }


}
