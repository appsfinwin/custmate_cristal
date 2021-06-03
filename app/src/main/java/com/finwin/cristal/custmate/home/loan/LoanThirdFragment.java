package com.finwin.cristal.custmate.home.loan;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.finwin.cristal.custmate.R;


public class LoanThirdFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_loan_three, container, false);

//        transid = (TextView) rootView.findViewById(R.id.tv_nef_transid);
//        withDrawalDate = (TextView) rootView.findViewById(R.id.tv_nef_date);
//
//        DebitAccountNumber = (TextView) rootView.findViewById(R.id.tv_nef_accno);
//        Name = (TextView) rootView.findViewById(R.id.tv_nef_name);
//        Mobile = (TextView) rootView.findViewById(R.id.tv_nef_mob);
//
//        CreditAccountNO = (TextView) rootView.findViewById(R.id.tv_nef_cr_acc);
//        WithdrawalAmount = (TextView) rootView.findViewById(R.id.tv_nef_amnt);
//
//        BtnOK = (Button) rootView.findViewById(R.id.btn_nef_ok);

        //================================================================================

//        TextView tv = (TextView) v.findViewById(R.id.tvFragThird);
//        tv.setText(getArguments().getString("msg"));

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        final String transactionID = intent.getStringExtra("transactionID");
//        final String debitAccountNumber = intent.getStringExtra("debit_account_number");
//        final String name = intent.getStringExtra("name");
//        final String mobile = intent.getStringExtra("mobile");

        final String creditAccountNumber = intent.getStringExtra("account_number");
        final String withdrawalAmount = intent.getStringExtra("withdrawalAmount");

        //================================================================================

//        transid.setText(transactionID);
//        try {
//            Date d = new Date();
//            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
//            String withdrawalDate = sdf.format(d);
//            withDrawalDate.setText(withdrawalDate);
//        } catch (Exception ignored) {
//        }
//        DebitAccountNumber.setText(ConstantClass.const_accountNumber);
//        Name.setText(ConstantClass.const_name);
//        Mobile.setText(ConstantClass.const_phone);
//
//        CreditAccountNO.setText(creditAccountNumber);
//        WithdrawalAmount.setText(withdrawalAmount);
//
//        BtnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                assert getFragmentManager() != null;
//                getFragmentManager().popBackStack();
//            }
//        });

    }

    public static LoanThirdFragment newInstance(String text) {
        LoanThirdFragment f = new LoanThirdFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }



}
