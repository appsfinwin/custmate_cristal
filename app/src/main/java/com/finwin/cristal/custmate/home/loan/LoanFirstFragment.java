package com.finwin.cristal.custmate.home.loan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoanFirstFragment extends Fragment {

    final Enc_crypter encr = new Enc_crypter();
    String Name, Mobile, respndMsg, message, demessage;
    RequestQueue requestQueue;
    SweetAlertDialog dialog;
    ArrayAdapter<String> adapter;
    TextView TVname, TVmobile, TVac_no;
    Spinner sp_acc;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_loan_one, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        sharedPreferences= getActivity().getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);

        TVname = rootView.findViewById(R.id.tv_lon_name);
        TVmobile = rootView.findViewById(R.id.tv_lon_mob);
        TVac_no = rootView.findViewById(R.id.tv_lon_accno);
        sp_acc = rootView.findViewById(R.id.Spnr_Acc_lon);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TVname.setText(
                sharedPreferences.getString(ConstantClass.userName,""));
        TVmobile.setText(
                sharedPreferences.getString(ConstantClass.phoneNumber,""));
        //TVac_no.setText(ConstantClass.const_accountNumber);

//        adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, const_acc_nos_array);
//        sp_acc.setAdapter(adapter);
//        sp_acc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//               String accNumber = const_acc_nos_array[position];
//                getAccountHolder(accNumber);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        BtnProceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                mViewPager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous
////                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//                StrAmount = EdtAmount.getText().toString();
//                transfer();
//            }
//        });

    }

    public static LoanFirstFragment newInstance(String text) {
        LoanFirstFragment f = new LoanFirstFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

}
