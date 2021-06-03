package com.finwin.cristal.custmate.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.activity_main.ActivityMain;


public class FundTransferReceipt extends FragmentActivity {

    TextView transid, CreditAccountNO, DebitAccountNumber,
            Name, Mobile, oldBal, WithdrawalAmount, currentBal, withDrawalDate;
    Button BtnOK;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_fund_transfer_receipt);

        sharedPreferences= getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        Intent intent = getIntent();
        final String transactionID = intent.getStringExtra("transactionID");
        final String withdrawalDate = intent.getStringExtra("transactionDate");

        final String debitAccountNumber = intent.getStringExtra("debit_account_number");
        final String name = intent.getStringExtra("name");
        final String mobile = intent.getStringExtra("mobile");
        final String old_balance = intent.getStringExtra("old_balance");
        final String current_balance = intent.getStringExtra("current_balance");

        final String creditAccountNumber = intent.getStringExtra("account_number");
        final String withdrawalAmount = intent.getStringExtra("withdrawalAmount");

        //================================================================================

        transid = (TextView) findViewById(R.id.tv_rc_transid);
        withDrawalDate = (TextView) findViewById(R.id.tv_rc_date);

        DebitAccountNumber = (TextView) findViewById(R.id.tv_rc_accno);
        Name = (TextView) findViewById(R.id.tv_rc_name);
        Mobile = (TextView) findViewById(R.id.tv_rc_mob);

        oldBal = (TextView) findViewById(R.id.tv_rc_pblnc);
        currentBal = (TextView) findViewById(R.id.tv_rc_cblnc);

        CreditAccountNO = (TextView) findViewById(R.id.tv_rc_cr_acc);
        WithdrawalAmount = (TextView) findViewById(R.id.tv_rc_amnt);

        BtnOK = (Button) findViewById(R.id.btn_rc_ok);

//      withdrawalTime = (TextView) findViewById(R.id.timeReceipt);
        //================================================================================

        transid.setText(transactionID);
        withDrawalDate.setText(withdrawalDate);

        DebitAccountNumber.setText(debitAccountNumber);
        Name.setText(name);
        Mobile.setText(mobile);
        oldBal.setText(old_balance);
        currentBal.setText(current_balance);

        CreditAccountNO.setText(creditAccountNumber);
        WithdrawalAmount.setText(withdrawalAmount);

        BtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                editor.putString("success","yes");
//                editor.commit();

                startActivity(new Intent(FundTransferReceipt.this, ActivityMain.class));
                finish();
            }
        });


    }





}
