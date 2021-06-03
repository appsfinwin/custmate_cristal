package com.finwin.cristal.custmate.home.transfer.search_beneficiary.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.databinding.BeneficiaryRowBinding;
import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.pojo.BeneficiaryData;

import java.util.Collections;
import java.util.List;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.ViewHolder> {

    List<BeneficiaryData> beneficiaryDataList;
    MutableLiveData<SearchBeneficiaryRowAction> mAction;

    public BeneficiaryAdapter() {
        beneficiaryDataList= Collections.emptyList();
        mAction=new MutableLiveData<>();
    }


    public void setBeneficiaryDataList(List<BeneficiaryData> beneficiaryDataList) {
        this.beneficiaryDataList = beneficiaryDataList;
        notifyDataSetChanged();
    }

    public MutableLiveData<SearchBeneficiaryRowAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<SearchBeneficiaryRowAction> mAction) {
        this.mAction = mAction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BeneficiaryRowBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.beneficiary_row,parent,false);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        BeneficiaryRowBinding binding;
        public ViewHolder(@NonNull BeneficiaryRowBinding binding ) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setBindingData(BeneficiaryData beneficiaryData, MutableLiveData<SearchBeneficiaryRowAction> mAction){

            if (binding.getViewmodel()==null)
            {
                binding.setViewmodel(new SearchBeneficiaryRowViewmodel(mAction,beneficiaryData));
            }else
            {
                binding.getViewmodel().setData(beneficiaryData);
            }
        }


    }
}
