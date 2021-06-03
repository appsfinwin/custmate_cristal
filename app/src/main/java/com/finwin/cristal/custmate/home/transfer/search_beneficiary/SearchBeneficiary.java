package com.finwin.cristal.custmate.home.transfer.search_beneficiary;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.databinding.BeneficiarySrchActvtyBinding;
import com.finwin.cristal.custmate.home.transfer.search_beneficiary.adapter.BeneficiaryAdapter;
import com.finwin.cristal.custmate.home.transfer.search_beneficiary.adapter.SearchBeneficiaryRowAction;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.view.View.VISIBLE;

public class SearchBeneficiary extends AppCompatActivity {

    ImageButton search_cancel, btn_search;
    String[] Strben_id, Strcustomer_id, Strben_name, Strben_mobile, Strben_account_no, Strben_ifsccode;
    String demessage, msg;
    RequestQueue requestQueue;
    private ListView ben_listView;
    TextView tvwrng_msg;
    EditText Edt_acc_no;
    ImageButton ibtn_benf_refresh;
    BeneficiaryAdapter benAdapter;
    private ProgressDialog dialog;
    static Enc_crypter encr = new Enc_crypter();
    BeneficiarySrchActvtyBinding binding;
    SearchBeneficiaryViewmodel viewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.beneficiary_srch_actvty);
        viewmodel=new ViewModelProvider(this).get(SearchBeneficiaryViewmodel.class);
        binding.setViewmodel(viewmodel);


        dialog = new ProgressDialog(SearchBeneficiary.this,R.style.AppCompatAlertDialogStyle);

        tvwrng_msg = findViewById(R.id.tv_wrng_msg);

        setRecyclerView(binding.rvBenList);

        search_cancel = findViewById(R.id.ibtn_benf_back);
        search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ibtn_benf_refresh = findViewById(R.id.ibtn_benf_refresh);
        ibtn_benf_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBeneficiary();
            }
        });

        getBeneficiary();

        viewmodel.getmAction().observe(this, new Observer<SearchBeneficairyAction>() {
            @Override
            public void onChanged(SearchBeneficairyAction searchBeneficairyAction) {

                dialog.dismiss();
                switch (searchBeneficairyAction.getAction())
                {
                    case SearchBeneficairyAction.BENEFICIARY_SUCCESS:
                        benAdapter.setBeneficiaryDataList(searchBeneficairyAction.getBeneficiaryListResponse().getData());
                        benAdapter.notifyDataSetChanged();
                        break;

                        case SearchBeneficairyAction.API_ERROR:
                            binding.rvBenList.setVisibility(View.INVISIBLE);
                                    tvwrng_msg.setVisibility(VISIBLE);
                                    tvwrng_msg.setText("Not found!");
                            Toast.makeText(SearchBeneficiary.this, "Server error occurred!", Toast.LENGTH_SHORT).show();
                                new SweetAlertDialog(SearchBeneficiary.this, SweetAlertDialog.ERROR_TYPE)
                                        .setContentText(searchBeneficairyAction.getError())
                                        .setTitleText("Error!")
                                        .show();
                }
            }
        });

    }

    private void setRecyclerView(RecyclerView rvBenList) {
        benAdapter=new BeneficiaryAdapter();
        rvBenList.setLayoutManager(new LinearLayoutManager(this));
        rvBenList.setHasFixedSize(true);
        rvBenList.setAdapter(benAdapter);

        setObservable(benAdapter);

    }

    private void setObservable(BeneficiaryAdapter benAdapter) {
        benAdapter.getmAction().observe(this, new Observer<SearchBeneficiaryRowAction>() {
            @Override
            public void onChanged(SearchBeneficiaryRowAction searchBeneficiaryRowAction) {
                if (searchBeneficiaryRowAction.getAction()==SearchBeneficiaryRowAction.CLICK_BENEFICIARY)
                {

                    Intent resultIntent = new Intent();
                                            resultIntent.putExtra("result_ben_name", searchBeneficiaryRowAction.getBeneficiaryData().getReceiverName());
                                            resultIntent.putExtra("result_ben_account_no", searchBeneficiaryRowAction.getBeneficiaryData().getReceiverAccountno());
                                            setResult(RESULT_OK, resultIntent);
                                            finish();

                }
            }
        });
    }


    @SuppressLint("ResourceType")
    private void getBeneficiary() {
        dialog.setMessage("Loading, Please wait...");
        dialog.show();
        viewmodel.getBeneficiary("oa");
//       // StringRequest postRequest = new StringRequest(Request.Method.POST, api_custSelectBeneficiary,// custSelectBeneficiary
//        StringRequest postRequest = new StringRequest(Request.Method.POST, api_custSndrRegredReceiversList,
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
//                            JSONObject jsonResponse = new JSONObject(demessage).getJSONObject("data");
//                            JSONArray jArray = jsonResponse.getJSONArray("Table");
//                            int jcount = jArray.length();
//                            Strben_id = new String[jcount];
//                            Strcustomer_id = new String[jcount];
//                            Strben_name = new String[jcount];
//                            Strben_mobile = new String[jcount];
//                            Strben_account_no = new String[jcount];
//                            Strben_ifsccode = new String[jcount];
//                            for (int i = 0; i < jcount; i++) {
//                                Strben_id[i] = jArray.getJSONObject(i).getString("ben_id");
//                                Strcustomer_id[i] = jArray.getJSONObject(i).getString("customer_id");
//                                Strben_name[i] = jArray.getJSONObject(i).getString("ben_name");
//                                Strben_mobile[i] = jArray.getJSONObject(i).getString("ben_mobile");
//                                Strben_account_no[i] = jArray.getJSONObject(i).getString("ben_account_no");
//                                Strben_ifsccode[i] = jArray.getJSONObject(i).getString("ben_ifsccode");
//                            }
//
//                            String JsonDataString = jsonResponse.toString();
//                            Log.e("JsonDataString", JsonDataString);
//                            msg = "ok";
//                        } catch (Exception e) {
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                            }
//                            e.printStackTrace();
//                            msg = "error";
//                            ErrorLog.submitError(SearchBeneficiary.this, this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//
//                        try {
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                            }
//                            switch (msg) {
//                                case "ok":
//                                    benAdapter = new BeneficiaryAdapter(SearchBeneficiary.this, Strben_id, Strcustomer_id, Strben_name,
//                                            Strben_mobile, Strben_account_no, Strben_ifsccode);
//                                    ben_listView.setAdapter(benAdapter);
//                                    ben_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                                        String Strben_id = benAdapter.getItem(position).toString();
////                                        String Strcustomer_id = benAdapter.getItem(position).toString();
//                                            String ben_name = benAdapter.getItem(position).toString();
//                                            String ben_account_no = benAdapter.getItem(position).toString();
//
//                                            Intent resultIntent = new Intent();
//                                            resultIntent.putExtra("result_ben_name", ben_name);
//                                            resultIntent.putExtra("result_ben_account_no", ben_account_no);
//                                            setResult(RESULT_OK, resultIntent);
//                                            finish();
//                                        }
//                                    });
//                                    tvwrng_msg.setVisibility(View.INVISIBLE);
//                                    ben_listView.setVisibility(VISIBLE);
//                                    break;
//                                case "0":
//                                    ben_listView.setVisibility(View.INVISIBLE);
//                                    tvwrng_msg.setVisibility(VISIBLE);
//                                    tvwrng_msg.setText("Not found!");
//                                    break;
//                                case "error":
//                                    Toast.makeText(SearchBeneficiary.this, "Server error occurred!", Toast.LENGTH_SHORT).show();
////                                new SweetAlertDialog(SearchBeneficiary.this, SweetAlertDialog.ERROR_TYPE)
////                                        .setContentText("Server error occurred!!")
////                                        .setTitleText("Error!")
////                                        .show();
//                                    break;
//                            }
//
//                        } catch (Exception e) {
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                            }
//                            e.printStackTrace();
//                            ErrorLog.submitError(SearchBeneficiary.this, this.getClass().getSimpleName() + ":" + new Object() {
//                            }.getClass().getEnclosingMethod().getName(), e.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        Log.d("ERROR", "error => " + error.toString());
//                        msg = "error";
//                        ErrorLog.submitError(SearchBeneficiary.this, this.getClass().getSimpleName() + ":" + new Object() {
//                        }.getClass().getEnclosingMethod().getName(), error.toString());
//                    }
//                }) {
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
//                items.put("ben_type", "oa");
//
//                Log.e("getBeneficiary: ", String.valueOf(items));
//                params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
//                Log.e("getBeneficiary: data", encr.conRevString(Enc_Utils.enValues(items)));
//                return params;
//            }
//        };
//        requestQueue.add(postRequest);
    }


}
