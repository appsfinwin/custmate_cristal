package com.finwin.cristal.custmate.home.transfer.view_beneficiary_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.databinding.ActivityBeneficiaryListBinding;
import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.action.BeneficiaryListAction;
import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.adapter.BeneficiaryListAdapter;
import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.adapter.BeneficiaryRowAction;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BeneficiaryListActivity extends AppCompatActivity {
    BeneficiaryListViewmodel viewmodel;
    ActivityBeneficiaryListBinding binding;
    BeneficiaryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_beneficiary_list);
        viewmodel = new ViewModelProvider(this).get(BeneficiaryListViewmodel.class);
        binding.setViewmodel(viewmodel);
        setupRecyclerview(binding.rvBenList);

        Intent intent=getIntent();
        String benType=intent.getStringExtra("benType");

        viewmodel.initLoading(this);
        viewmodel.getBeneficiary(benType);


        viewmodel.getmAction().observe(this, new Observer<BeneficiaryListAction>() {
            @Override
            public void onChanged(BeneficiaryListAction beneficiaryListAction) {

                viewmodel.cancelLoading();
                switch (beneficiaryListAction.getAction()) {
                    case BeneficiaryListAction.BENE_LIST_SUCCESS:
                        adapter.setBeneficiaryDataList(beneficiaryListAction.getBeneficiaryListResponse().getData());
                        adapter.notifyDataSetChanged();
                        break;

                    case BeneficiaryListAction.API_ERROR:
                        new SweetAlertDialog(BeneficiaryListActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ERROR!")
                                .setContentText(beneficiaryListAction.getError())
                                .show();
                        break;

                    case BeneficiaryListAction.CLICK_BACK:
                        finish();
                        break;
                }
            }
        });
    }

    private void setupRecyclerview(RecyclerView rvBenList) {

        adapter = new BeneficiaryListAdapter();
        rvBenList.setLayoutManager(new LinearLayoutManager(this));
        rvBenList.setAdapter(adapter);

        setObservable(adapter);
    }

    private void setObservable(BeneficiaryListAdapter adapter) {
        adapter.getmAction().observe(this, new Observer<BeneficiaryRowAction>() {
            @Override
            public void onChanged(final BeneficiaryRowAction beneficiaryRowAction) {

                if (beneficiaryRowAction.getAction() == BeneficiaryRowAction.CLICK_DELETE) {

                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(BeneficiaryListActivity.this);
                    //alertDialog2.setTitle("Confirm SignOut");
                    alertDialog2.setMessage("Are you sure you want to delete?");
                    alertDialog2.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(BeneficiaryListActivity.this, beneficiaryRowAction.getData().getReceiverName() + " deleted", Toast.LENGTH_SHORT).show();
                                }
                            });
                    alertDialog2.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.cancel();
                                }
                            });
                    alertDialog2.show();

                }
            }
        });
    }
}