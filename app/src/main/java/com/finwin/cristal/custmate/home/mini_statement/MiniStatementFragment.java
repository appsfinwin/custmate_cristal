package com.finwin.cristal.custmate.home.mini_statement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.finwin.cristal.custmate.AdapterAndModel.Adapters.MiniStmntAdapter;
import com.finwin.cristal.custmate.AdapterAndModel.Models.MiniStmntListModel;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.databinding.FrgMiniStatementBinding;
import com.finwin.cristal.custmate.home.mini_statement.action.MiniStatementAction;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MiniStatementFragment extends Fragment {
    final Enc_crypter encr = new Enc_crypter();
    private ArrayList<MiniStmntListModel> stmntModelArrayList;
    private MiniStmntAdapter bAdapter;
    RequestQueue requestQueue;
    SweetAlertDialog pDialog;
    ImageButton iBtn_back;
    private RecyclerView recyclerView;
    MiniStatementViewmodel viewmodel;
    FrgMiniStatementBinding binding;
    MiniStmntAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater, R.layout.frg_mini_statement, container, false);
        viewmodel=new ViewModelProvider(getActivity()).get(MiniStatementViewmodel.class);
        binding.setViewmodel(viewmodel);


        requestQueue = Volley.newRequestQueue(getActivity());
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);


        recyclerView = binding.recViewStmnt;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new MiniStmntAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        viewmodel.getMiniStatement();
        viewmodel.initLoading(getActivity());
        stmntModelArrayList = new ArrayList<>();
       // AsyncMiniStatement();

        iBtn_back = binding.ibtnBack;
        iBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel.getmAction().observe(getViewLifecycleOwner(), new Observer<MiniStatementAction>() {
            @Override
            public void onChanged(MiniStatementAction miniStatementAction) {
                viewmodel.cancelLoading();
                switch (miniStatementAction.getAction())
                {
                    case MiniStatementAction.MINI_STATEMENT_SUCCESS:

                        binding.tvaccBalance.setText(miniStatementAction.getMiniStatementResponse().getMiniStatement().getData().getCURRENTBALANCE());
                        binding.tvaccNo.setText(miniStatementAction.getMiniStatementResponse().getMiniStatement().getData().getACCNO());
                        adapter.setTransactonList(miniStatementAction.getMiniStatementResponse().getMiniStatement().getData().getTRANSACTONS());
                        adapter.notifyDataSetChanged();
                        break;


                        case MiniStatementAction.API_ERROR:

                            break;
                }
            }
        });
    }

//    private void AsyncMiniStatement() {
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading..");
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        StringRequest postRequest = new StringRequest(Request.Method.POST, api_getMiniStatement,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonFrstRespns = new JSONObject(response);
//                            if (jsonFrstRespns.has("data")) {
//                                demessage = decValues(encr.revDecString(jsonFrstRespns.getString("data")));
//                                Log.e("demessage", demessage);
//                            }
//                            JSONObject jsonResponse = new JSONObject(demessage);
//                            JSONObject receipt = jsonResponse.getJSONObject("mini_statement");
//                            final JSONObject data = receipt.getJSONObject("data");
//                            final JSONArray jsonArray = data.getJSONArray("TRANSACTONS");
//
//                            TvAcc_no.setText(data.getString("ACC_NO"));
////                                        name.setText(data.getString("NAME"));
////                                        mobile.setText(data.getString("MOBILE"));
//                            TvAcc_balance.setText(data.getString("CURRENT_BALANCE"));
//
//                            int count = jsonArray.length();
//                            SAtxnid = new String[count];
//                            SAdate = new String[count];
//                            SAtrnsacTyp = new String[count];
//                            SAdebitORCre = new String[count];
//                            SAamount = new String[count];
//                            SAbalance = new String[count];
//
//                            for (int i = 0; i < count; i++) {
//                                JSONObject json_data = null;
//                                try {
//
//                                    json_data = jsonArray.getJSONObject(i);
//                                    SAtxnid[i] = json_data.getString("TXN_ID");
//                                    SAdate[i] = json_data.getString("TXN_DATE");
//                                    SAtrnsacTyp[i] = json_data.getString("TRAN_TYPE");
//                                    SAdebitORCre[i] = json_data.getString("DORC");
//                                    SAamount[i] = json_data.getString("TXN_AMOUNT");
//                                    SAbalance[i] = json_data.getString("BALANCE");
//
//                                    MiniStmntListModel stmntModel = new MiniStmntListModel(SAtxnid[i], SAdate[i], SAtrnsacTyp[i], SAdebitORCre[i], SAamount[i], SAbalance[i]);
//                                    stmntModelArrayList.add(stmntModel);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
////                            for (int i = 0; i < count; i++) {
//////                                MiniStmntListModel beanClassForRecyclerView_contacts = new MiniStmntListModel(iconImage[i], debitLogo[i], billname[i], phnNo[i], SAamount[i], SAbalance[i]);
////                                MiniStmntListModel beanClassForRecyclerView_contacts = new MiniStmntListModel(SAtxnid[i], SAdate[i], SAtrnsacTyp[i], SAdebitORCre[i], SAamount[i], SAbalance[i]);
////                                stmntModelArrayList.add(beanClassForRecyclerView_contacts);
////                            }
//
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(getActivity(), "Some Error Occurred!", Toast.LENGTH_SHORT).show();
//                            ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//                        pDialog.dismiss();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), "Some Error Occurred!", Toast.LENGTH_SHORT).show();
//                ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
//                }.getClass().getEnclosingMethod().getName(), error.toString());
//            }
//        }) {
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
//    }


}
