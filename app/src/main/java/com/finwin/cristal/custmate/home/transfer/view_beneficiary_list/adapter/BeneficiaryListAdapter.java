package com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.databinding.LayoutBeneficiaryListBinding;
import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryData;

import java.util.Collections;
import java.util.List;

public class BeneficiaryListAdapter extends RecyclerView.Adapter<BeneficiaryListAdapter.ViewHolder> {
    List<BeneficiaryData> beneficiaryDataList;
    MutableLiveData<BeneficiaryRowAction> mAction;

    public BeneficiaryListAdapter() {
        beneficiaryDataList= Collections.emptyList();
        mAction=new MutableLiveData<>();
    }

    public void setBeneficiaryDataList(List<BeneficiaryData> beneficiaryDataList) {
        this.beneficiaryDataList = beneficiaryDataList;
        notifyDataSetChanged();
    }

    public MutableLiveData<BeneficiaryRowAction> getmAction() {
        return mAction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutBeneficiaryListBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_beneficiary_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setBindingData(beneficiaryDataList.get(position),mAction);
    }

    @Override
    public int getItemCount() {
        return beneficiaryDataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        LayoutBeneficiaryListBinding binding;
        public ViewHolder(@NonNull LayoutBeneficiaryListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setBindingData(BeneficiaryData beneficiaryData, MutableLiveData<BeneficiaryRowAction> mAction) {

            if (binding.getViewmodel()==null)
            {
                binding.setViewmodel(new BeneficiaryRowViewmodel(mAction,beneficiaryData));
            }else
            {
                binding.getViewmodel().setData(beneficiaryData);
            }
        }
    }
}
