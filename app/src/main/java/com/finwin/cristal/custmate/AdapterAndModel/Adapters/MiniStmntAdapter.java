package com.finwin.cristal.custmate.AdapterAndModel.Adapters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.Collections;
import java.util.List;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.home.mini_statement.pojo.TRANSACTON;

public class MiniStmntAdapter extends RecyclerView.Adapter<MiniStmntAdapter.MyViewHolder> {
    Context context;
    List<TRANSACTON> transactonList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView iconImage, debitLogo;
        TextView txnid, date, trnsacTyp, debitORCre, amount, balance;

        public MyViewHolder(View view) {
            super(view);
            txnid = (TextView) view.findViewById(R.id.list_transid);
            date = (TextView) view.findViewById(R.id.list_date);
            trnsacTyp = (TextView) view.findViewById(R.id.list_trans_type);
            debitORCre = (TextView) view.findViewById(R.id.list_dorc);
            amount = (TextView) view.findViewById(R.id.list_amount);
            balance = (TextView) view.findViewById(R.id.list_blnce);

//            iconImage = (ImageView) view.findViewById(R.id.iconImage);
//            debitLogo = (ImageView) view.findViewById(R.id.debitLogo);
        }
    }

//    public MiniStmntAdapter(Context mainActivityContacts, ArrayList<MiniStmntListModel> offerList) {
//        this.OfferList = offerList;
//        this.context = mainActivityContacts;
//    }

    public MiniStmntAdapter(Context context) {
        this.context=context;
        this.transactonList= Collections.emptyList();
    }

    public void setTransactonList(List<TRANSACTON> transactonList) {
        this.transactonList = transactonList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactonList.size();
    }

    @Override
    public MiniStmntAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.frg_mini_statement_item, parent, false);
        return new MiniStmntAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniStmntAdapter.MyViewHolder holder, int position) {
        TRANSACTON lists = transactonList.get(position);
        Log.e("onBindViewHolder: ", String.valueOf(lists.getTXNID()));

        holder.txnid.setText(lists.getTXNID());
        holder.date.setText(lists.getTXNDATE());
        holder.trnsacTyp.setText(lists.getTRANTYPE());
        holder.amount.setText(lists.getTXNAMOUNT());
        holder.balance.setText(lists.getBALANCE());
        if (lists.getDORC().equals("D")) {
            holder.debitORCre.setText("Dr");
            holder.debitORCre.setTextColor(context.getResources().getColor(R.color.text_red));
        } else if (lists.getDORC().equals("C")) {
            holder.debitORCre.setText("Cr");
            holder.debitORCre.setTextColor(context.getResources().getColor(R.color.text_green));
        }

//        holder.iconImage.setImageResource(lists.getIconImage());
//        holder.debitLogo.setImageResource(lists.getDebitLogo());
    }


}


