package com.finwin.cristal.custmate.home.reacharge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;


import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RechargeFragmentReceipt extends FragmentActivity {

    TextView transid, CreditAccountNO, DebitAccountNumber,Date,
            Name, Mobile, WithdrawalAmount;
    Button BtnOK;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_recharge_receipt);

        sharedPreferences= getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        DebitAccountNumber = (TextView) findViewById(R.id.tv_rec_accno);
        Date = (TextView) findViewById(R.id.tv_rec_date);
        Name = (TextView) findViewById(R.id.tv_rec_name);
        Mobile = (TextView) findViewById(R.id.tv_rec_mob);
        CreditAccountNO = (TextView) findViewById(R.id.tv_rec_cr_acc);
        WithdrawalAmount = (TextView) findViewById(R.id.tv_rec_amnt);
        BtnOK = (Button) findViewById(R.id.btn_rec_ok);

        Intent intent = getIntent();
        final String transactionID = intent.getStringExtra("transactionID");
        final String creditAccountNumber = intent.getStringExtra("account_number");
        final String withdrawalAmount = intent.getStringExtra("Rech_Amount");

        //================================================================================

//        transid.setText(transactionID);
        try {
            Date d = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            String withdrawalDate = sdf.format(d);
            Date.setText(withdrawalDate);
        } catch (Exception ignored) {
        }
        DebitAccountNumber.setText(sharedPreferences.getString(ConstantClass.accountNumber,""));
        Name.setText(
                sharedPreferences.getString(ConstantClass.userName,""));
        Mobile.setText(
                sharedPreferences.getString(ConstantClass.phoneNumber,""));

        CreditAccountNO.setText(creditAccountNumber);
        WithdrawalAmount.setText(withdrawalAmount);

        BtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }





}
