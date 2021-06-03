package com.finwin.cristal.custmate.home.loan;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.finwin.cristal.custmate.R;


public class LoanSecondFragment  extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_loan_two, container, false);
//        requestQueue = Volley.newRequestQueue(getContext());
//        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        BtnTransfer = (Button) rootView.findViewById(R.id.btn_transfer);

//        TextView tv = (TextView) v.findViewById(R.id.tvFragSecond);
//        assert getArguments() != null;
//        tv.setText(getArguments().getString("msg"));

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        BtnTransfer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestingTransaction();
//            }
//        });

    }

    public static LoanSecondFragment newInstance(String text) {
        LoanSecondFragment f = new LoanSecondFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }


}
