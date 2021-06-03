package com.finwin.cristal.custmate.home.transfer.view_recent_transfers.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.databinding.LayoutRowTransactionListBinding;
import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.pojo.transaction_list.TransactionData;

import java.util.Collections;
import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {
    List<TransactionData> transactionDataList;
    MutableLiveData<TransactionListRowAction> mAction;

    public TransactionListAdapter() {
        transactionDataList= Collections.emptyList();
        mAction=new MutableLiveData<>();
    }

    public MutableLiveData<TransactionListRowAction> getmAction() {
        return mAction;
    }

    public void setTransactionDataList(List<TransactionData> transactionDataList) {
        this.transactionDataList = transactionDataList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutRowTransactionListBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_row_transaction_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setBindData(transactionDataList.get(position),mAction);
    }

    @Override
    public int getItemCount() {
        return transactionDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutRowTransactionListBinding binding;
        public ViewHolder(@NonNull LayoutRowTransactionListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setBindData(TransactionData data,MutableLiveData<TransactionListRowAction> mAction)
        {
            if (binding.getViewmodel()==null)
            {
                binding.setViewmodel(new TransactionListRowViewmodel(data,mAction));
            }else {
                binding.getViewmodel().setData(data);
            }
        }
    }
}
