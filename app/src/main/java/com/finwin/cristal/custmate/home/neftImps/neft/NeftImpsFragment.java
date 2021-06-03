package com.finwin.cristal.custmate.home.neftImps.neft;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
;
import com.finwin.cristal.custmate.databinding.FrgFundTransOneBinding;
import com.finwin.cristal.custmate.home.transfer.transfer_account_otp.FundTransferAccOTP;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import online.devliving.passcodeview.PasscodeView;

public class NeftImpsFragment extends Fragment {

    Enc_crypter encr = new Enc_crypter();
    Button BtnProceed;
    EditText EdtAmount;
    Spinner SpnrNeft, spinner_transfer_type;

    RequestQueue requestQueue;
    SweetAlertDialog dialog, beneficiaryLoading, verifyMpinLoading, loadingDialog;
    String StrAmount, beneid = "",selectedTransferTyp = "";
    ConstraintLayout layout_account_etails;
    TextView tv_account_number, tv_ifsc;
    NeftViewmodel viewmodel;
    FrgFundTransOneBinding binding;
    Dialog verifyMpinDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_fund_trans_one, container, false);

        sharedPreferences= getActivity().getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

        binding = DataBindingUtil.inflate(inflater, R.layout.frg_fund_trans_one, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);

        SpnrNeft = rootView.findViewById(R.id.neftSpinner);
        spinner_transfer_type = rootView.findViewById(R.id.spinner_transfer_type);
        BtnProceed = (Button) rootView.findViewById(R.id.btn_proceed_otp);
        EdtAmount = rootView.findViewById(R.id.edt_amount);
        layout_account_etails = rootView.findViewById(R.id.layout_account_details);
        tv_account_number = rootView.findViewById(R.id.tv_account_number);
        tv_ifsc = rootView.findViewById(R.id.tv_ifsc);
        viewmodel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(NeftViewmodel.class);
        binding.setViewmodel(viewmodel);
        viewmodel.setBinding(binding);
        loadingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
//        tv.setText(getArguments().getString("msg"));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBeneficiaryList();
        BtnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mViewPager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous
//                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                StrAmount = EdtAmount.getText().toString();
                //transfer();
                if ((!selectedTransferTyp.equals("")) && (!beneid.equals("")) && (!EdtAmount.getText().toString().equals(""))) {
                    verifyMpin();
                }
            }
        });

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
//                R.layout.spinner_list_item);
//        adapter.setDropDownViewResource(R.layout.spinner_list_item);
//        binding.spinnerTransferType.setAdapter(adapter);


        viewmodel.getmAction().observe(getActivity(), new Observer<NeftAction>() {
            @Override
            public void onChanged(NeftAction neftAction) {

                switch (neftAction.getAction()) {
                    case NeftAction.BENEFICIARY_LIST_SUCCESS:
                        beneficiaryLoading.dismiss();
                        viewmodel.setBeneficiaryList(neftAction.getBeneficiaryListResponse().getData());
                        break;

                    case NeftAction.VALIDATE_MPIN_SUCCESS:
                        verifyMpinLoading.dismiss();
                        generateOTP();
                        break;

                    case NeftAction.GENERATE_OTP_SUCCESS:

                        loadingDialog.dismiss();

                        Intent intent = new Intent(getActivity(), FundTransferAccOTP.class);
                        intent.putExtra("from", "neft");
                        intent.putExtra("account_number",
                                sharedPreferences.getString(ConstantClass.accountNumber,""));
                        intent.putExtra("cr_account_no", viewmodel.beneficiary_account.get());//crAcno
                        intent.putExtra("process_amount", EdtAmount.getText().toString());
                        intent.putExtra("agent_id",
                                sharedPreferences.getString(ConstantClass.custId,""));
                        intent.putExtra("ben_id", beneid);
                        intent.putExtra("TXN_PAYMODE", viewmodel.transferType_id.get());

                        intent.putExtra("otp_data", neftAction.genarateOtpResponse.getOtp().getData());
                        intent.putExtra("otp_id", neftAction.genarateOtpResponse.getOtp().getOtpId());
                        startActivity(intent);

                        break;

                    case NeftAction.CLICK_PROCEED:
                        verifyMpin();
                        break;
                        case NeftAction.API_ERROR:
                            if (beneficiaryLoading!=null)
                            {
                                beneficiaryLoading.dismiss();
                            }
                        break;
                }
            }
        });


        // setTransferType();

    }

//    private void setTransferType() {
//
//        transferTypeList.add(new ModelTransferType("--Select Transfer Type--",""));
//        transferTypeList.add(new ModelTransferType("RTGS","RT"));
//        transferTypeList.add(new ModelTransferType("NEFT","NE"));
//        transferTypeList.add(new ModelTransferType("IMPS","PA"));
//        transferTypeList.add(new ModelTransferType("Fund Transfer(AXIS bank only)","FT"));
//
//        transferArray.add("--Select Transfer Type--");
//        transferArray.add("RTGS");
//        transferArray.add("NEFT");
//        transferArray.add("IMPS");
//        transferArray.add("Fund Transfer(AXIS bank only)");
//
//
//
////        binding.spinnerTransferType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view,
////                                       int position, long id) {
////                if (position != 0) {
////                    int pos = parent.getSelectedItemPosition();
////                    selectedTransferTyp=transferTypeList.get(pos).getTransferId();
//////                    tv_account_number.setText(modelSpinnerList.get(pos).getAccount_number());
////                }
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////                Toast.makeText(getContext(), "Nothing Selected!", Toast.LENGTH_SHORT).show();
////            }
////        });
//    }

    public static NeftImpsFragment newInstance(String text) {
        NeftImpsFragment f = new NeftImpsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    public void getBeneficiaryList() {
        beneficiaryLoading = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        beneficiaryLoading.setTitleText("Loading");
        beneficiaryLoading.setCancelable(false);
        beneficiaryLoading.setContentText("Fetching Beneficiary Details..").show();
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("customer_id",
                sharedPreferences.getString(ConstantClass.custId,""));
        items.put("ben_name", "");
        items.put("ben_mobile", "");
        items.put("ben_account_no", "");
        items.put("ben_ifscode", "");
        items.put("ben_type", "ob");

        Log.e("getBeneficiary: ", String.valueOf(items));
        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
        Log.e("getBeneficiary: data", encr.conRevString(Enc_Utils.enValues(items)));
        viewmodel.getBeneficiary(params);
    }


    public void verifyMpin() {
        verifyMpinDialog = new Dialog(getActivity());
        verifyMpinDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        verifyMpinDialog.setCancelable(true);
        verifyMpinDialog.setTitle("Enter mPIN");
        verifyMpinDialog.setContentView(R.layout.mpin_verification);

//        Button dialogButton = (Button) dialog.findViewById(R.id.dlg_ok);
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        PasscodeView passcodeView = verifyMpinDialog.findViewById(R.id.passcode_view);
        if (passcodeView.requestFocus()) {
            verifyMpinDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        passcodeView.setPasscodeEntryListener(new PasscodeView.PasscodeEntryListener() {
            @Override
            public void onPasscodeEntered(String passcode) {
                verifyMpinDialog.dismiss();
                //validateMpin(passcode, verifyMpinDialog);
                verifyMpin(passcode);
            }
        });
        verifyMpinDialog.show();
    }

    public void verifyMpin(String mpin) {
        verifyMpinLoading = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        verifyMpinLoading.setTitleText("Please wait")
                .setContentText("validating..")
                .show();

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("userid",
                sharedPreferences.getString(ConstantClass.accountNumber,""));
        items.put("MPIN", mpin);

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
        Log.e("mpin post", encr.conRevString(Enc_Utils.enValues(items)));
        viewmodel.validateMpin(params);
    }


    public void generateOTP() {
        loadingDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingDialog.setTitleText("Transferring..");
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("particular", "mob");
        items.put("account_no",
                sharedPreferences.getString(ConstantClass.accountNumber,""));
        items.put("amount", EdtAmount.getText().toString());
        items.put("agent_id",
                sharedPreferences.getString(ConstantClass.custId,""));
        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        Log.e("OTPGenerate: ", String.valueOf(items));
        Log.e("OTPGenerate data", String.valueOf(params));

        viewmodel.generateOTP(params);

    }
//    private void generateOTP(String s) {
//        loadingDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        loadingDialog.setTitleText("Transferring..");
//        loadingDialog.setCancelable(false);
//        loadingDialog.show();
//
//        BtnProceed.setEnabled(true);
//
//        requestQueue = Volley.newRequestQueue(getActivity());
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
//                            } else {
//                                TrMsg = "error";
//                            }
//                        } catch (Exception e) {
//                            loadingDialog.dismiss();
//                            e.printStackTrace();
//                            TrMsg = "NoInternet";
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//
//                        try {
//                            loadingDialog.dismiss();
//                            switch (TrMsg) {
//                                case "InvalidOtp":
//                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                                            .setContentText(error)
//                                            .setTitleText("Error!")
//                                            .show();
//                                    break;
//                                case "error":
//                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                                            .setContentText("Server Error Occurred!")
//                                            .setTitleText("Error!")
//                                            .show();
//                                    break;
//                                case "success":
//                                    Intent intent = new Intent(getActivity(), FundTransferAccOTP.class);
//                                    intent.putExtra("from", "neft");
//                                    intent.putExtra("account_number", ConstantClass.const_accountNumber);
//                                    intent.putExtra("cr_account_no", sbacc_no);//crAcno
//                                    intent.putExtra("process_amount",EdtAmount.getText().toString());
//                                    intent.putExtra("agent_id", ConstantClass.const_cusId);
//                                    intent.putExtra("ben_id", beneid);
//                                    intent.putExtra("TXN_PAYMODE", selectedTransferTyp);
//
//                                    intent.putExtra("otp_data", StrOTP_data);
//                                    intent.putExtra("otp_id", StrOTP_id);
//                                    startActivity(intent);
//                                    break;
//                                default:
//                                    break;
//                            }
//                        } catch (Exception e) {
//                            loadingDialog.dismiss();
//                            e.printStackTrace();
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        loadingDialog.dismiss();
//                        Log.d("ERROR", "error => " + error.toString());
//                        TrMsg = "NoInternet";
//                        ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                        }.getClass().getEnclosingMethod().getName(), error.toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                // the POST parameters:
//                Map<String, String> params = new HashMap<>();
//                Map<String, String> items = new HashMap<>();
//                items.put("particular", "mob");
//                items.put("account_no", ConstantClass.const_accountNumber);
//                items.put("amount", EdtAmount.getText().toString());
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
//
//    private void getList() {
//
//        beneficiaryLoading.setTitleText("Loading");
//        beneficiaryLoading.setCancelable(false);
//        beneficiaryLoading.setContentText("Fetching Beneficiary Details..").show();
////        final String benurl = ip_global + "/custSenderRegisteredReceiversList";
//
//        modelSpinnerList.clear();
//        StringRequest postRequest = new StringRequest(Request.Method.POST, api_custSndrRegredReceiversList,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonFrstRespns = new JSONObject(response);
//                            if (jsonFrstRespns.has("data")) {
//                                demessage = decValues(encr.revDecString(jsonFrstRespns.getString("data")));
//                                Log.e("demessage", demessage);
//
//                                modelSpinnerList.add(new ModelSpinner("","",""));
//                                beneArray.add("--Select Beneficiary--");
//                            }
//                            JSONObject jsonResponse = new JSONObject(demessage);
//                            if (jsonResponse.has("data")) {
//                                String status = jsonResponse.getString("status");
//                                Log.e("STATUS=", status);
//                                final JSONArray jsonArray = jsonResponse.getJSONArray("data");
//                                Log.e("JSONArray=>", jsonArray.toString());
//                                int length = jsonArray.length();
//                                benidArray = new String[length];
//                                for (int i = 0; i < length; i++) {
//                                    JSONObject jsonData = jsonArray.getJSONObject(i);
//                                    Log.e("JData", jsonData.toString());
//                                    sbid = jsonData.getString("receiverid");
//                                    sbname = jsonData.getString("receiver_name");
//                                    benidArray[i] = sbname + "," + sbid;
//                                    sbacc_no = jsonData.getString("receiver_accountno");
//                                    sbifsc = jsonData.getString("receiver_ifsccode");
//                                    beneArray.add(sbname);
//                                    modelSpinnerList.add(new ModelSpinner(sbacc_no,sbifsc,sbid));
//                                    respndMsg = "ok";
//                                }
//                            }
//                            if (jsonResponse.has("msg")) {
//                                msg = jsonResponse.getString("msg");
//                                Log.e("reader  has  msg!", msg);
//                                respndMsg = "error";
//                            }
//                        } catch (JSONException e) {
//                            dialog.dismiss();
//                            e.printStackTrace();
//                            respndMsg = "error";
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        } catch (Exception e) {
//                            dialog.dismiss();
//                            e.printStackTrace();
//                            respndMsg = "error";
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//
//                        try {
//                            dialog.dismiss();
//                            if (respndMsg.equals("ok")) {
//                                Log.e("ARRAY=>", Arrays.toString(benidArray));
//                                try {
//                                    int length = benidArray.length;
//                                    Log.e("LENGTH => ", String.valueOf(length));
//                                    benArray = new String[length + 1];
//                                    benArray[0] = "--Select Beneficiary--";
//
//
//                                    for (int i = 0; i < length; i++) {
//                                        int indSize = benidArray[i].lastIndexOf(",");
//                                        benArray[i + 1] = benidArray[i].substring(0, indSize);
//                                        Log.e("trim", benArray[i]);
//
//                                    }
//
//                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
//                                            R.layout.spinner_list_item, beneArray);
//                                    SpnrNeft.setAdapter(adapter);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                                    }.getClass().getEnclosingMethod().getName(), e.toString());
//                                }
//                                SpnrNeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view,
//                                                               int position, long id) {
//                                        if (position != 0) {
//                                            int pos = parent.getSelectedItemPosition();
//                                            String value = benidArray[pos - 1];
//                                            //beneid = value.substring(value.lastIndexOf(',') + 1);
//                                            beneid = modelSpinnerList.get(pos).getBen_id();
//                                            layout_account_etails.setVisibility(View.VISIBLE);
//                                            tv_account_number.setText(modelSpinnerList.get(pos).getAccount_number());
//                                            tv_ifsc.setText(modelSpinnerList.get(pos).getIfsc());
//                                            EdtAmount.setEnabled(true);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//                                        Toast.makeText(getContext(), "Nothing Selected!", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                            if (respndMsg.equals("error")) {
//                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                                        .setTitleText("Error")
//                                        .setContentText(msg)
//                                        .show();
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        dialog.dismiss();
//                        Log.d("ERROR", "error => " + error.toString());
//                        respndMsg = "error";
//                        ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                        }.getClass().getEnclosingMethod().getName(), error.toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                // the POST parameters:
//                Map<String, String> params = new HashMap<>();
//                Map<String, String> items = new HashMap<>();
//                items.put("customer_id", ConstantClass.const_cusId);
//                items.put("ben_name", "");
//                items.put("ben_mobile", "");
//                items.put("ben_account_no", "");
//                items.put("ben_ifscode", "");
//                items.put("ben_type", "ob");
//
//                Log.e("getBeneficiary: ", String.valueOf(items));
//                params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
//                Log.e("getBeneficiary: data", encr.conRevString(Enc_Utils.enValues(items)));
//                return params;
//            }
//        };
//        requestQueue.add(postRequest);
//
//    }
//
//    private void transfer_neft() {
//
//        dialog.setTitleText("Loading");
//        dialog.setCancelable(false);
//        dialog.setContentText("Generating OTP").show();
//
//        requestQueue = Volley.newRequestQueue(getActivity());
//        StringRequest postRequest = new StringRequest(Request.Method.POST, api_custRequestingTransaction,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonFrstRespns = new JSONObject(response);
//                            if (jsonFrstRespns.has("data")) {
//                                demessage = decValues(encr.revDecString(jsonFrstRespns.getString("data")));
//                                Log.e("demessage", demessage);
//                            }
//
//                            JSONObject jsonResponse = new JSONObject(demessage);
//                            String status = jsonResponse.getString("status");
//                            Log.e("status", status);
//                            if (status.equals("1")) {
//                                msg_data = jsonResponse.getString("msg");
//                                txn_id = jsonResponse.getString("user_txn_id");
//                                transRmsg = "1";
//                            } else {
//                                msg_data = jsonResponse.getString("msg");
//                                transRmsg = "0";
//                            }
//                        } catch (JSONException e) {
//                            dialog.dismiss();
//                            e.printStackTrace();
//                            transRmsg = "0";
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        } catch (Exception e) {
//                            dialog.dismiss();
//                            e.printStackTrace();
//                            transRmsg = "0";
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//
//                        try {
//                            dialog.dismiss();
//                            if (transRmsg.equals("1")) {
//                                viewPager.setCurrentItem(1, true);
//                            }
//                            if (transRmsg.equals("0")) {
//                                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
//                                        .setTitleText("Error")
//                                        .setContentText(msg_data)
//                                        .show();
//                            }
////                            otplinear.setVisibility(View.VISIBLE);
//                        } catch (Exception e) {
//                            dialog.dismiss();
//                            e.printStackTrace();
//                            transRmsg = "0";
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        Log.d("ERROR", "error => " + error.toString());
//                        dialog.dismiss();
//                        transRmsg = "0";
//                        ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                        }.getClass().getEnclosingMethod().getName(), error.toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                // the POST parameters:
//                Map<String, String> params = new HashMap<>();
//                Map<String, String> items = new HashMap<>();
//                items.put("customer_id", ConstantClass.const_cusId);
//                items.put("account_no", ConstantClass.const_accountNumber);
//                items.put("ben_id", beneid);
//                items.put("amount", StrAmount);
//                items.put("mode", "AUTO");
//
//                params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
//                return params;
//            }
//        };
//        requestQueue.add(postRequest);
//    }
//
//    private void validateMpin(String _mpin, final Dialog simpleDialog) {
//        // BtnProceed.setEnabled(false);
//        final String mpin = _mpin;
//        final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
//        sweetAlertDialog.setTitleText("Please wait")
//                .setContentText("validating..")
//                .show();
//
////        requestQueue = Volley.newRequestQueue(getContext());
//        StringRequest postRequest = new StringRequest(Request.Method.POST, api_ValidateMPIN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.e("onResponse ==" + getActivity(), response);
//                        try {
//                            JSONObject jsonFrstRespns = new JSONObject(response);
//                            if (jsonFrstRespns.has("data")) {
//                                demessage = decValues(encr.revDecString(jsonFrstRespns.getString("data")));
//                                Log.e("demessage", demessage);
//
//                                sweetAlertDialog.dismissWithAnimation();
//                                simpleDialog.dismiss();
//                                try {
//                                    JSONObject jsonResponse = new JSONObject(demessage);
//                                    if (jsonResponse.getBoolean("value")) {
//                                        //=====
////                                        Transfer();
////                                        generateOTP();
//                                        //=====
//                                    } else {
//                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                                                .setTitleText("Invalid!")
//                                                .setContentText("Invalid MPIN")
//                                                .show();
//                                    }
//                                } catch (JSONException e) {
//                                    BtnProceed.setEnabled(true);
//                                    e.printStackTrace();
//                                    sweetAlertDialog.dismissWithAnimation();
//                                    simpleDialog.dismiss();
//                                    Toast.makeText(getContext(), "Some Error Occurred!", Toast.LENGTH_SHORT).show();
//                                }
//                                Log.d("", response.toString());
//                            }
//                        } catch (Exception e) {
//                            BtnProceed.setEnabled(true);
//                            e.printStackTrace();
//                            sweetAlertDialog.dismissWithAnimation();
//                            simpleDialog.dismiss();
//                            Toast.makeText(getContext(), "Some Error Occurred!", Toast.LENGTH_SHORT).show();
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        BtnProceed.setEnabled(true);
//                        Log.d("ERROR", "error => " + error.toString());
//                        sweetAlertDialog.dismissWithAnimation();
//                        simpleDialog.dismiss();
//                        Toast.makeText(getContext(), "Some Error Occurred!", Toast.LENGTH_SHORT).show();
//                        ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                        }.getClass().getEnclosingMethod().getName(), error.toString());
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // the POST parameters:
//                Map<String, String> params = new HashMap<>();
//                Map<String, String> items = new HashMap<>();
//                items.put("userid", ConstantClass.const_cusId);
//                items.put("MPIN", mpin);
//
//                params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
//                Log.e("mpin post", encr.conRevString(Enc_Utils.enValues(items)));
//                return params;
//            }
//        };
//        requestQueue.add(postRequest);
//    }
}
