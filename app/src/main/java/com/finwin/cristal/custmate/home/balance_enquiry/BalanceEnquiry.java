package com.finwin.cristal.custmate.home.balance_enquiry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.finwin.cristal.custmate.R;

import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.databinding.FrgBalanceEnquiryBinding;
import com.finwin.cristal.custmate.home.balance_enquiry.action.BalanceAction;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BalanceEnquiry extends Fragment {
    Enc_crypter encr = new Enc_crypter();
    SweetAlertDialog pDialog, dialogue;
    RequestQueue requestQueue;

    TextView TvDate, TvTime, TvAcc_no, TvName, TvMobile, TvBalance;
    ImageButton iBtn_back;
    String demessage, rspndMsg,
            jdate, jaccount_no, jname, jphone, jbalance;

    BalanceViewmodel viewmodel;
    FrgBalanceEnquiryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.frg_balance_enquiry, container, false);
        viewmodel = new ViewModelProvider(getActivity()).get(BalanceViewmodel.class);
        binding.setViewmodel(viewmodel);

        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        requestQueue = Volley.newRequestQueue(getActivity());

        viewmodel.balanceEnquiry();
        viewmodel.initLoading(getActivity());
        TvDate = binding.tvdate;
        TvTime = binding.tvtime;
        TvAcc_no = binding.tvaccNumber;
        TvName = binding.tvaccName;
//        TvMobile = rootview.findViewById(R.id.tvdate);
        TvBalance = binding.tvbalance;



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel.getmAction().observe(getViewLifecycleOwner(), new Observer<BalanceAction>() {
            @Override
            public void onChanged(BalanceAction balanceAction) {

                viewmodel.cancelLoading();
                switch (balanceAction.getAction()) {
                    case BalanceAction.GET_BALANCE_SUCCESS:

                        viewmodel.setBalance(balanceAction.getBalanceResponse().getBalance());
                        break;
                    case BalanceAction.API_ERROR:
                        dialogue = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                        dialogue.setTitleText("Error!!");
                        dialogue.setContentText(balanceAction.getError());
                        dialogue.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                FragmentManager fm = getFragmentManager();
                                fm.popBackStack();
                                dialogue.dismiss();
                            }
                        }).show();
                        break;

                        case BalanceAction.CLICK_BACK:
                            getFragmentManager().popBackStack();
                            break;
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        viewmodel.getmAction().setValue(new BalanceAction(BalanceAction.DEFAULT));
    }

//    private void AsyncBalanceEnquiry() {
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        StringRequest postRequest = new StringRequest(Request.Method.POST, api_balanceEnqury,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonFrstRespns = new JSONObject(response);
//                            if (jsonFrstRespns.has("data")) {
//                                demessage = decValues(encr.revDecString(jsonFrstRespns.getString("data")));
//                                Log.e("demessage", demessage);
//                            }
//                            JSONObject jsonResponse = new JSONObject(demessage).getJSONObject("balance");
//                            if (jsonResponse.has("data")) {
//                                JSONObject data = jsonResponse.getJSONObject("data");
//                                jdate = data.getString("DATE");
//                                jaccount_no = data.getString("ACC_NO");
//                                jname = data.getString("NAME");
//                                jphone = data.getString("MOBILE");
//                                jbalance = data.getString("CURRENT_BALANCE");
//                                rspndMsg = "ok";
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            rspndMsg = "error";
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            rspndMsg = "error";
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//
//                        try {
//                            if (rspndMsg.equals("error")) {
//                                pDialog.dismiss();
//                                dialogue = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
//                                dialogue.setTitleText("Error!!");
//                                dialogue.setContentText("Invalid Account Number!");
//                                dialogue.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                        FragmentManager fm = getFragmentManager();
//                                        fm.popBackStack();
//                                        dialogue.dismiss();
//                                    }
//                                }).show();
//
//                            } else {
//                                String date = jdate.substring(0, 10);
//                                String time = jdate.substring(10, 19);
//                                pDialog.dismiss();
//                                TvDate.setText(date);
//                                TvTime.setText(time);
//                                TvAcc_no.setText(ConstantClass.const_accountNumber);
//                                TvName.setText(jname);
////                                TvMobile.setText(jphone);
//                                TvBalance.setText(jbalance);
//                            }
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
//                        Log.d("ERROR", "error => " + error.toString());
//                        rspndMsg = "error";
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
//                items.put("account_no", ConstantClass.const_accountNumber);
//
//                params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
//                return params;
//            }
//        };
//        requestQueue.add(postRequest);
//
//    }

}
