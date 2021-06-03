package com.finwin.cristal.custmate.sign_up.sign_up.old_classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivitySignUpDetails extends Fragment {

    RequestQueue requestQueue;
    SweetAlertDialog dialog;
    Button btnSignup;
    TextView tvSignin;

    String respndMsg, respMsgApi, DecKey, DecKeyGrdl, message;
    final Enc_crypter encr = new Enc_crypter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_signup_detail, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);

        tvSignin = rootView.findViewById(R.id.tv_signin);
        btnSignup = rootView.findViewById(R.id.btn_signup);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SignUpActivity.viewPager.setCurrentItem(1, true);
////                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//                registerAPI();
            }
        });


//        BtnProceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                mViewPager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous
////                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//
//            }
//        });

    }

    public static ActivitySignUpDetails newInstance(String text) {
        ActivitySignUpDetails f = new ActivitySignUpDetails();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }


//    public void registerAPI() {
//        dialog.setTitleText("Registering User").show();
//        StringRequest postRequest = new StringRequest(Request.Method.GET, api_url_APIkey,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Log.e("JSONResult : ", response);
//                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("data");
//                            if (jsonResponse.has("key")) {
//                                message = jsonResponse.getString("key");
//                                DecKey = encr.revDecString(message);
//                                DecKeyGrdl = encr.revDecString(encr.decrypter(BuildConfig.AP_KE));
//                                if (DecKeyGrdl.equals(DecKey)) {
//                                    Log.e("registerAPI", "same");
//                                    respMsgApi = "Registered";
////                                    RegisterUser(respMsgApi);
//                                }
////                                Enc_Utils.apSav(ActivitySignUp.this, message);
////                                respMsgApi = "Registered";
//                            }
//                            if (jsonResponse.has("error")) {
//                                message = jsonResponse.getString("error");
//                                respMsgApi = "ErrorInConnection";
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            ErrorLog.submitError(getContext(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//                        try {
//                            if (respMsgApi.equals("ErrorInConnection")) {
//                                dialog.dismiss();
//                                new SweetAlertDialog(Objects.requireNonNull(getContext()), SweetAlertDialog.BUTTON_NEGATIVE)
//                                        .setTitleText("ERROR")
//                                        .setContentText("Error in connection.")
//                                        .show();
//                            }
//                        } catch (Exception e) {
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
//                        Log.d("ERROR", "error => " + error.toString());
//                        ErrorLog.submitError(getContext(), this.getClass().getSimpleName() + ":" + new Object() {
//                        }.getClass().getEnclosingMethod().getName(), error.toString());
//                    }
//                }
//        );
//        requestQueue.add(postRequest);
//    }

}
