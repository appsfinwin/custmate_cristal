package com.finwin.cristal.custmate.home.transfer.view_recent_transfers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.databinding.ActivityRecentTransfersBinding;
import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.action.RecentTransactionsAction;
import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.adapter.TransactionListAdapter;
import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.adapter.TransactionListRowAction;
import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_list.TransactionData;
import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.transaction_details.TransactionDetailsActivity;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RecentTransfersActivity extends AppCompatActivity {

    RecentTransfersViewmodel viewmodel;
    ActivityRecentTransfersBinding binding;
    TransactionListAdapter transactionListAdapter;
    SweetAlertDialog sweetAlertDialog;

    Disposable disposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_recent_transfers);
        viewmodel=new ViewModelProvider(this).get(RecentTransfersViewmodel.class);
        binding.setViewmodel(viewmodel);
        setRecyclerView(binding.rvTransactionList);
        viewmodel.initLoading(this);
        viewmodel.getRecentTransactions();




        viewmodel.getmAction().observe(this, new Observer<RecentTransactionsAction>() {
            @Override
            public void onChanged(RecentTransactionsAction recentTransactionsAction) {
                //cancelProgress();

                switch (recentTransactionsAction.getAction())
                {
                    case RecentTransactionsAction.TRANSACTIONLIST_SUCCESS:
                        transactionListAdapter.setTransactionDataList(recentTransactionsAction.getTransactionListResponse().getBen().getData());
                        //transactionListAdapter.setTransactionDataList(ConstantClass.transactionListResponse.getBen().getData());
                        transactionListAdapter.notifyDataSetChanged();
                        viewmodel.cancelLoading();

                        break;

                        case RecentTransactionsAction.TRANSACTIONLIST_EMPTY:
                            viewmodel.cancelLoading();
                            new SweetAlertDialog(RecentTransfersActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("ERROR")
                                    .setContentText("No transaction found!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            finish();
                                        }
                                    })
                                    .show();
                            break;

                            case RecentTransactionsAction.BACK_PRESSED:
                                finish();
                                break;
                }
            }
        });

    }


    public void setData()
    {



    }
    private List<TransactionData> setReyclerData(List<TransactionData> data) {

       return data;

    }

    private void setRecyclerView(RecyclerView rvTransactionList) {

        transactionListAdapter=new TransactionListAdapter();
        rvTransactionList.setLayoutManager(new LinearLayoutManager(this));
        rvTransactionList.setAdapter(transactionListAdapter);

        setObservable(transactionListAdapter);

    }

    private void setObservable(TransactionListAdapter transactionListAdapter) {
        transactionListAdapter.getmAction().observe(this, new Observer<TransactionListRowAction>() {
            @Override
            public void onChanged(TransactionListRowAction transactionListRowAction) {
                switch (transactionListRowAction.getAction())
                {
                    case TransactionListRowAction.ITEM_CLICK:
                    {
                        Intent intent=new Intent(RecentTransfersActivity.this, TransactionDetailsActivity.class);
                        intent.putExtra("cust_unique_number",transactionListRowAction.getTransactionData().getCustomerUniqueNo());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    @Override
    protected void onResume() {
        super.onResume();
        //showProgress();
        //viewmodel.getRecentTransactions();
    }
}