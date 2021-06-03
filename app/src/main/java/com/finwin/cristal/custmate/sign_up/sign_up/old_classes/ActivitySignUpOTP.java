package com.finwin.cristal.custmate.sign_up.sign_up.old_classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivitySignUpOTP extends Fragment {

    Enc_crypter encr = new Enc_crypter();
    RequestQueue requestQueue;
    SweetAlertDialog sweetDialog;

    Button btn_SubmitOTP;
    EditText edt_OTP;
    TextView tv_ReSendOTP;
    String demessage, TrMsg, error,
            StrOTP_data, StrOTP_id,
            Str_OTP;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_signup_otp, container, false);
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        sweetDialog = new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.PROGRESS_TYPE);

        edt_OTP = rootView.findViewById(R.id.edt_signup_otp);
        tv_ReSendOTP = rootView.findViewById(R.id.tv_signup_resend_otp);
        btn_SubmitOTP = rootView.findViewById(R.id.btn_signup_sbmt);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_SubmitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_OTP.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter OTP", Toast.LENGTH_LONG).show();
                } else {
                    btn_SubmitOTP.setEnabled(false);
                    Str_OTP = edt_OTP.getText().toString();
//                    Transfer();
                }
            }
        });

        tv_ReSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ReSendOTP.setEnabled(false);
//                re_generateOTP();
            }
        });
    }

    public static ActivitySignUpOTP newInstance(String text) {
        ActivitySignUpOTP f = new ActivitySignUpOTP();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

//    private void re_generateOTP() {
//        sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        sweetDialog.setTitleText("Transferring..");
//        sweetDialog.setCancelable(false);
//        sweetDialog.show();
//
//        StringRequest postRequest = new StringRequest(Request.Method.POST, api_OTPGenerate,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonFrstRespns = new JSONObject(response);
//                            if (jsonFrstRespns.has("data")) {
//                                demessage = decValues(encr.revDecString(jsonFrstRespns.getString("data")));
//                                Log.e("demessage OTPGenerate", demessage);
//                            }
//
//                            JSONObject jsonResponse = new JSONObject(demessage);
//                            if (jsonResponse.has("otp")) {
//                                JSONObject jobj2 = jsonResponse.getJSONObject("otp");
//                                if (jobj2.has("error")) {
//                                    error = jobj2.getString("error");
//                                    TrMsg = "InvalidOtp";
//                                } else {
//                                    StrOTP_data = jobj2.getString("data");
//                                    StrOTP_id = jobj2.getString("otp_id");
//                                    TrMsg = "success";
//                                }
//
//                            } else {
//                                TrMsg = "error";
//                            }
//                        } catch (Exception e) {
//                            tv_ReSendOTP.setEnabled(true);
//                            sweetDialog.dismiss();
//                            e.printStackTrace();
//                            TrMsg = "NoInternet";
//                            ErrorLog.submitError(getContext(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//
//                        try {
//                            tv_ReSendOTP.setEnabled(true);
//                            sweetDialog.dismiss();
//                            switch (TrMsg) {
//                                case "InvalidOtp":
//                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
//                                            .setContentText(error)
//                                            .setTitleText("Error!")
//                                            .show();
//                                    break;
//                                case "error":
//                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
//                                            .setContentText("Server Error Occurred!")
//                                            .setTitleText("Error!")
//                                            .show();
//                                    break;
//                                case "success":
//                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
//                                            .setContentText("OTP sent successfully!")
//                                            .setTitleText("Success")
//                                            .show();
//                                    break;
//                                default:
//                                    break;
//                            }
//                        } catch (Exception e) {
//                            tv_ReSendOTP.setEnabled(true);
//                            sweetDialog.dismiss();
//                            e.printStackTrace();
//                            ErrorLog.submitError(getContext(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        tv_ReSendOTP.setEnabled(true);
//                        sweetDialog.dismiss();
//                        Log.d("ERROR", "error => " + error.toString());
//                        TrMsg = "NoInternet";
//                        ErrorLog.submitError(getContext(), this.getClass().getSimpleName() + ":" + new Object() {
//                        }.getClass().getEnclosingMethod().getName(), error.toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                // the POST parameters:
//                Map<String, String> params = new HashMap<>();
//                Map<String, String> items = new HashMap<>();
//                items.put("particular", "mob_resent");
//                items.put("account_no", ConstantClass.const_accountNumber);
//                items.put("amount", "");
//                items.put("agent_id", ConstantClass.const_cusId);
//                params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
//
//                Log.e("OTPGenerate: ", String.valueOf(items));
//                Log.e("OTPGenerate data", String.valueOf(params));
//
//                return params;
//            }
//        };
//        requestQueue.add(postRequest);
//    }

}
