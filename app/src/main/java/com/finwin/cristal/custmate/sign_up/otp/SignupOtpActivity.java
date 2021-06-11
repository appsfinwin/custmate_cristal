package com.finwin.cristal.custmate.sign_up.otp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.finwin.cristal.custmate.ActivityInitial;
import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.databinding.ActivitySignupOtpBinding;
import com.finwin.cristal.custmate.sign_up.otp.action.SignupOtpAction;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignupOtpActivity extends AppCompatActivity {

    SignupOtpViewmodel viewmodel;
    ActivitySignupOtpBinding binding;
    String otpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_signup_otp);
        viewmodel=new ViewModelProvider(this).get(SignupOtpViewmodel.class);
        binding.setViewmodel(viewmodel);

        Intent intent=getIntent();
        otpId=intent.getStringExtra("otpId");
        //viewmodel.generateOtp();

        viewmodel.getmAction().observe(this, new Observer<SignupOtpAction>() {
            @Override
            public void onChanged(SignupOtpAction signupOtpAction) {
                switch (signupOtpAction.getAction())
                {
                    case SignupOtpAction.GENERATE_OTP_SUCCESS:
                        otpId=signupOtpAction.getGenarateOtpResponse().getOtp().getOtpId();
                        Toast.makeText(SignupOtpActivity.this, "otp sent successfully", Toast.LENGTH_SHORT).show();
                        break;

                    case SignupOtpAction.CLICK_PROCEED:
                        viewmodel.register(otpId);
                        break;

                        case SignupOtpAction.REGISTER_SUCCESS:
                            new SweetAlertDialog(SignupOtpActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setContentText(signupOtpAction.getRegisterResponse().getUser().getData().getMsg())
                                    .setTitleText("Success")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            Intent intent=new Intent(SignupOtpActivity.this, ActivityInitial.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .show();
                            break;

                            case SignupOtpAction.SIGNUP_ERROR:
                                new SweetAlertDialog(SignupOtpActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setContentText(signupOtpAction.getError())
                                        .setTitleText("Error")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                finish();
                                            }
                                        })
                                        .show();
                                break;
                }
            }
        });

    }
}