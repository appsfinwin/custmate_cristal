package com.finwin.cristal.custmate.home.transfer.transfer_account_otp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.home.FundTransferReceipt;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FundTransferAccOTP extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    Enc_crypter encr = new Enc_crypter();
    RequestQueue requestQueue;
    SweetAlertDialog sweetDialog,dialog;

    Button btn_SendOTP;
    EditText edt_OTP;
    TextView tv_ReSendOTP;
    String receiptTransferID, receiptTransferDate, receiptDebitAccno, receiptCreditAccno,
            receiptName, receiptMobile, receiptOldBalance, receiptWithdrawalAmount, receiptCurrentBalance,
            Str_OTP = "",
            StrAccNo, StrCrAccNo, StrProcess_amnt, Str_agent_id, Str_otp_data, Str_otp_id,
            StrOTP_data,from,TXN_PAYMODE,beneid;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;




    private static final String TAG = "FundTransferAccOTP";
    FundTranferOtpViewmodel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_fund_transfer_otpview);
        requestQueue = Volley.newRequestQueue(this);
        sweetDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sharedPreferences= getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        viewmodel=new ViewModelProvider((ViewModelStoreOwner) this).get(FundTranferOtpViewmodel.class);
        Intent intent = getIntent();
        from=intent.getStringExtra("from");
        StrAccNo = intent.getStringExtra("account_number");
        StrCrAccNo = intent.getStringExtra("cr_account_no");
        StrProcess_amnt = intent.getStringExtra("process_amount");
        Str_agent_id = intent.getStringExtra("agent_id");
        Str_otp_data = intent.getStringExtra("otp_data");
        Str_otp_id = intent.getStringExtra("otp_id");
        beneid=intent.getStringExtra("ben_id");
        TXN_PAYMODE=intent.getStringExtra("TXN_PAYMODE");


        viewmodel.getmAction().observe(this, new Observer<FundTransferOtpAction>() {
            @Override
            public void onChanged(FundTransferOtpAction fundTransferOtpAction) {

               cancelLoading();
                switch (fundTransferOtpAction.getAction())
                {
                    case FundTransferOtpAction.NEFT_SUCCESS:

                    case FundTransferOtpAction.ACCOUNT_TRANSFER_SUCCESS:


                        receiptTransferID = fundTransferOtpAction.getNeftTransferResponse().getReceipt().getData().getTranId();
                        receiptTransferDate = fundTransferOtpAction.getNeftTransferResponse().getReceipt().getData().getTransferDate();
                        receiptDebitAccno = fundTransferOtpAction.getNeftTransferResponse().getReceipt().getData().getAccNo();
                        receiptCreditAccno = fundTransferOtpAction.getNeftTransferResponse().getReceipt().getData().getBenAccNo();
                        receiptName = fundTransferOtpAction.getNeftTransferResponse().getReceipt().getData().getName();
                        receiptMobile = fundTransferOtpAction.getNeftTransferResponse().getReceipt().getData().getMobile();
                        receiptOldBalance = fundTransferOtpAction.getNeftTransferResponse().getReceipt().getData().getOldBalance();
                        receiptWithdrawalAmount = fundTransferOtpAction.getNeftTransferResponse().getReceipt().getData().getWithdrawalAmount();
                        receiptCurrentBalance = fundTransferOtpAction.getNeftTransferResponse().getReceipt().getData().getCurrentBalance();


                        Intent intent = new Intent(FundTransferAccOTP.this, FundTransferReceipt.class);
                        intent.putExtra("transactionID", receiptTransferID);
                        intent.putExtra("transactionDate", receiptTransferDate);
                        intent.putExtra("account_number", receiptDebitAccno);
                        intent.putExtra("debit_account_number", receiptCreditAccno);
                        intent.putExtra("name", receiptName);
                        intent.putExtra("mobile", receiptMobile);
                        intent.putExtra("old_balance", receiptOldBalance);
                        intent.putExtra("withdrawalAmount", receiptWithdrawalAmount);
                        intent.putExtra("current_balance", receiptCurrentBalance);
                        startActivity(intent);
                        finish();

                        break;
                        case FundTransferOtpAction.API_ERROR:

                            new SweetAlertDialog(FundTransferAccOTP.this, SweetAlertDialog.ERROR_TYPE)
                                    .setContentText(fundTransferOtpAction.getError())
                                    .setTitleText("Error")
                                    .show();
                            break;

                            case FundTransferOtpAction.OTP_SUCCESS:

                                StrOTP_data = fundTransferOtpAction.getResendOtpResponse().getOtp().getData();
                                Str_otp_id =  fundTransferOtpAction.getResendOtpResponse().getOtp().getOtpId();
                                new SweetAlertDialog(FundTransferAccOTP.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setContentText("OTP sent successfully!")
                                        .setTitleText("Success")
                                        .show();
                                break;


                }
            }
        });

        edt_OTP = findViewById(R.id.edt_trnsfr_otp);
        tv_ReSendOTP = findViewById(R.id.tv_resend_otp);
        btn_SendOTP = findViewById(R.id.btn_trnsfr_snd_otp);

        btn_SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_OTP.getText().toString().equals("")) {
                    Toast.makeText(FundTransferAccOTP.this, "Please Enter OTP", Toast.LENGTH_LONG).show();
                } else {

                    Str_OTP = edt_OTP.getText().toString();

                    if (from.equals("neft")){
                       // transfer_neft();
                        neftApiCall();
                    }else if (from.equals("account")) {
                        //Transfer();
                        accountTransfer();
                    }
                }
            }
        });

        tv_ReSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
            }
        });

    }

    public void neftApiCall(){

        dialog.setTitleText("Loading");
        dialog.setCancelable(false);
        dialog.setContentText("Transferring..").show();

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();

        items.put("ben_id", beneid);
        items.put("otp_val", Str_OTP);
        items.put("otp_id", Str_otp_id);
        items.put("account_no", StrAccNo);
        items.put("beni_account_no", StrCrAccNo);
        items.put("process_amount", StrProcess_amnt);
        items.put("agent_id", Str_agent_id);
        items.put("transferType", "own");
        items.put("TXN_PAYMODE", TXN_PAYMODE);

        String request=(new JSONObject(items)).toString();
        Log.d(TAG, "neft: "+request);

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
        String request1=(new JSONObject(params)).toString();
        viewmodel.neftApi(params);
    }

    public void  accountTransfer()
    {
        dialog.setTitleText("Loading");
        dialog.setCancelable(false);
        dialog.setContentText("Transferring..").show();

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();

        items.put("otp_val", Str_OTP);
        items.put("otp_id", Str_otp_id);
        items.put("account_no", StrAccNo);
        items.put("beni_account_no", StrCrAccNo);
        // beni
        items.put("process_amount", StrProcess_amnt);
        items.put("agent_id", Str_agent_id);
        items.put("transferType", "own");
        //
//                params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        String request=(new JSONObject(items)).toString();
        Log.d(TAG, "transfer_request: "+request);
        String data = encr.conRevString(Enc_Utils.enValues(items));
        Log.e("getParams: data =", data);
        params.put("data", data);
        String request1=(new JSONObject(params)).toString();
        Log.d(TAG, "transfer_request: "+request);
        viewmodel.accountTransfer(params);
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            editor.putString("success","no");
            editor.commit();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Click again to cancel the payment", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    public void resendOtp(){
        sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetDialog.setTitleText("Requesting OTP");
        sweetDialog.setCancelable(false);
        sweetDialog.show();

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("particular", "mob_resent");
        items.put("account_no",
                sharedPreferences.getString(ConstantClass.accountNumber,""));
        items.put("amount", StrProcess_amnt);
        items.put("agent_id", sharedPreferences.getString(ConstantClass.custId,""));



        String request=(new JSONObject(items)).toString();
        Log.d(TAG, "transfer_request: "+request);
        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        viewmodel.resendOtp(params);

    }
//    private void re_generateOTP() {
//        sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        sweetDialog.setTitleText("Transferring..");
//        sweetDialog.setCancelable(false);
//        sweetDialog.show();
//
//
//        requestQueue = Volley.newRequestQueue(FundTransferAccOTP.this);
//        StringRequest postRequest = new StringRequest(Request.Method.POST, api_OTPGenerate,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonFrstRespns = new JSONObject(response);
//                            if (jsonFrstRespns.has("data")) {
//                                demessage = decValues(encr.revDecString(jsonFrstRespns.getString("data")));
//                                Log.d(TAG, "otp_response: "+demessage);
//                                Log.e("demessage OTPGenerate", demessage);
//                            }
//
//                            JSONObject jsonResponse = new JSONObject(demessage);
//
//                            String request=jsonResponse.toString();
//                            Log.d(TAG, "transfer_response: "+request);
//                            if (jsonResponse.has("otp")) {
//                                JSONObject jobj2 = jsonResponse.getJSONObject("otp");
//                                if (jobj2.has("error")) {
//                                    error = jobj2.getString("error");
//                                    TrMsg = "InvalidOtp";
//                                } else {
//                                    StrOTP_data = jobj2.getString("data");
//                                    Str_otp_id = jobj2.getString("otp_id");
//
//                                    TrMsg = "success";
//                                }
//
////                                if (jsonResponse.has("data")) {
////                                    JSONObject jobj2 = jsonResponse.getJSONObject("data");
////                                    StrOTP_data = jobj2.getString("data");
////                                    StrOTP_id = jobj2.getString("otp_id");
////                                    TrMsg = "success";
////                                }
//                            } else {
//                                TrMsg = "error";
//                            }
//                        } catch (Exception e) {
//                            tv_ReSendOTP.setEnabled(true);
//                            sweetDialog.dismiss();
//
//                            e.printStackTrace();
//                            TrMsg = "NoInternet";
//                            ErrorLog.submitError(FundTransferAccOTP.this, this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//
//                        try {
//                            tv_ReSendOTP.setEnabled(true);
//                            sweetDialog.dismiss();
//                            switch (TrMsg) {
//                                case "InvalidOtp":
//                                    new SweetAlertDialog(FundTransferAccOTP.this, SweetAlertDialog.ERROR_TYPE)
//                                            .setContentText(error)
//                                            .setTitleText("Error!")
//                                            .show();
//                                    break;
//                                case "error":
//                                    new SweetAlertDialog(FundTransferAccOTP.this, SweetAlertDialog.ERROR_TYPE)
//                                            .setContentText("Server Error Occurred!")
//                                            .setTitleText("Error!")
//                                            .show();
//                                    break;
//                                case "success":
//                                    new SweetAlertDialog(FundTransferAccOTP.this, SweetAlertDialog.SUCCESS_TYPE)
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
//                            ErrorLog.submitError(FundTransferAccOTP.this, this.getClass().getSimpleName() + ":" + new Object() {
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
//                        ErrorLog.submitError(FundTransferAccOTP.this, this.getClass().getSimpleName() + ":" + new Object() {
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
//                items.put("amount", StrProcess_amnt);
//                items.put("agent_id", ConstantClass.const_cusId);
//
//
//
//                String request=(new JSONObject(items)).toString();
//                Log.d(TAG, "transfer_request: "+request);
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

//    private void Transfer() {
//        sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        sweetDialog.setTitleText("Transferring..");
//        sweetDialog.setCancelable(false);
//        sweetDialog.show();
//
//        requestQueue = Volley.newRequestQueue(FundTransferAccOTP.this);
//        StringRequest postRequest = new StringRequest(Request.Method.POST, api_cashTransfer,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonFrstRespns = new JSONObject(response);
//                            if (jsonFrstRespns.has("data")) {
//                                demessage = decValues(encr.revDecString(jsonFrstRespns.getString("data")));
//                                Log.e("demessage Transfer", demessage);
//
//                                Log.d(TAG, "transfer_response: "+demessage);
//                            }
//                            JSONObject jsonResponse = new JSONObject(demessage).getJSONObject("receipt");
//
//                            if (jsonResponse.has("data")) {
//                                JSONObject jobj2 = jsonResponse.getJSONObject("data");
//                                Log.e("Receipt", jobj2.toString());
//                                receiptTransferID = jobj2.getString("TRAN_ID");
//                                receiptTransferDate = jobj2.getString("TRANSFER_DATE");
//                                receiptDebitAccno = jobj2.getString("ACC_NO");
//                                receiptCreditAccno = jobj2.getString("BEN_ACC_NO");
//                                receiptName = jobj2.getString("NAME");
//                                receiptMobile = jobj2.getString("MOBILE");
//                                receiptOldBalance = jobj2.getString("OLD_BALANCE");
//                                receiptWithdrawalAmount = jobj2.getString("WITHDRAWAL_AMOUNT");
//                                receiptCurrentBalance = jobj2.getString("CURRENT_BALANCE");
//
//                                TrMsg = "success";
//                            }
//                            if (jsonResponse.has("error")) {
//                                error = jsonResponse.getString("error");
//                                TrMsg = "InvalidOtp";
//                            }
//                        } catch (Exception e) {
//                            btn_SendOTP.setEnabled(true);
//                            sweetDialog.dismiss();
//                            e.printStackTrace();
//                            TrMsg = "NoInternet";
//                            ErrorLog.submitError(FundTransferAccOTP.this, this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//
//                        try {
//                            btn_SendOTP.setEnabled(true);
//                            sweetDialog.dismiss();
//                            if (TrMsg.equals("InvalidOtp")) {
//                                new SweetAlertDialog(FundTransferAccOTP.this, SweetAlertDialog.ERROR_TYPE)
//                                        .setContentText(error)
//                                        .setTitleText("Error!")
//                                        .show();
//                            } else {
//                                //Log.e("Printed",errorCode);
//                                //Toast.makeText(ctx, "errorCode ", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(FundTransferAccOTP.this, FundTransferReceipt.class);
//                                intent.putExtra("transactionID", receiptTransferID);
//                                intent.putExtra("transactionDate", receiptTransferDate);
//                                intent.putExtra("account_number", receiptDebitAccno);
//                                intent.putExtra("debit_account_number", receiptCreditAccno);
//                                intent.putExtra("name", receiptName);
//                                intent.putExtra("mobile", receiptMobile);
//                                intent.putExtra("old_balance", receiptOldBalance);
//                                intent.putExtra("withdrawalAmount", receiptWithdrawalAmount);
//                                intent.putExtra("current_balance", receiptCurrentBalance);
//                                startActivity(intent);
//                                finish();
//                            }
//                        } catch (Exception e) {
//                            btn_SendOTP.setEnabled(true);
//                            sweetDialog.dismiss();
//                            e.printStackTrace();
//                            ErrorLog.submitError(FundTransferAccOTP.this, this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        btn_SendOTP.setEnabled(true);
//                        sweetDialog.dismiss();
//                        Log.d("ERROR", "error => " + error.toString());
//                        TrMsg = "NoInternet";
//                        ErrorLog.submitError(FundTransferAccOTP.this, this.getClass().getSimpleName() + ":" + new Object() {
//                        }.getClass().getEnclosingMethod().getName(), error.toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                // the POST parameters:
//                Map<String, String> params = new HashMap<>();
//                Map<String, String> items = new HashMap<>();
//
//                items.put("otp_val", Str_OTP);
//                items.put("otp_id", Str_otp_id);
//                items.put("account_no", StrAccNo);
//                items.put("beni_account_no", StrCrAccNo);
//                // beni
//                items.put("process_amount", StrProcess_amnt);
//                items.put("agent_id", Str_agent_id);
//                items.put("transferType", "own");
//                //
////                params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
//
//                Log.e("getParams: ", String.valueOf(items));
//                String data = encr.conRevString(Enc_Utils.enValues(items));
//                Log.e("getParams: data =", data);
//                params.put("data", data);
//                return params;
//            }
//        };
//        requestQueue.add(postRequest);
//    }

    public void cancelLoading()
    {
        if(dialog!=null)
        {
            dialog.dismiss();
            //dialog=null;
        }

        if(sweetDialog!=null)
        {
            sweetDialog.dismiss();
            //dialog=null;
        }
    }
}