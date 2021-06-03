package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.transaction_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.databinding.ActivityTransactionDetailsBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TransactionDetailsActivity extends AppCompatActivity {

    String cust_unique_number;
    TransactionDetailsViewmodel viewmodel;
    ActivityTransactionDetailsBinding binding;
    SweetAlertDialog sweetAlertDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_transaction_details);
        viewmodel=new ViewModelProvider(this).get(TransactionDetailsViewmodel.class);
        binding.setViewmodel(viewmodel);

        Intent intent=getIntent();
        cust_unique_number=intent.getStringExtra("cust_unique_number");

        showProgress();
        viewmodel.getRecentTransactionStatus(cust_unique_number);

        viewmodel.getmAction().observe(this, new Observer<TransactionDetailsAction>() {
            @Override
            public void onChanged(TransactionDetailsAction transactionDetailsAction) {
                cancelProgress();
                switch (transactionDetailsAction.getAction())
                {
                    case  TransactionDetailsAction.TRANSACTION_DETAILS:
                        viewmodel.setData(transactionDetailsAction.getTransactionStatusResponse().getBen().getData().get(0));
                        break;

                    case TransactionDetailsAction.API_ERROR:
                        new SweetAlertDialog(TransactionDetailsActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ERROR")
                                .setContentText( transactionDetailsAction.getError())
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        finish();
                                    }
                                })
                                .show();
                break;

                case TransactionDetailsAction.BACK_PRESSED:
                    finish();
                    break;
                }
            }
        });
    }

    private void showProgress() {

        sweetAlertDialog = new SweetAlertDialog(TransactionDetailsActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("Please wait")
                .show();;
    }
      private void cancelProgress() {
        if (sweetAlertDialog!=null)
        {
            sweetAlertDialog.dismiss();
        }
    }


}