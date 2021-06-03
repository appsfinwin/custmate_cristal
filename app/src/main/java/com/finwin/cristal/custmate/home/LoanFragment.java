package com.finwin.cristal.custmate.home;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.finwin.cristal.custmate.AdapterAndModel.LoanViewPageAdapter;
import com.finwin.cristal.custmate.R;


import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoanFragment extends Fragment {

    ImageButton iBtn_back;
    RequestQueue requestQueue;
    SweetAlertDialog dialog;
    public static ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.frg_loan_main, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        iBtn_back = rootview.findViewById(R.id.ibtn_back_loan);
        viewPager = (ViewPager) rootview.findViewById(R.id.viewPagerLoan);

        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setAdapter(new LoanViewPageAdapter(getChildFragmentManager()));

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            public void onPageScrollStateChanged(int state) {
//            }
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//            public void onPageSelected(int position) {
//                // Check if this is the page you want.
//                Log.e("onPageSelected: ", String.valueOf(position));
//                switch (position) {
//                    case 0:
//                        iBtn_back.setVisibility(View.VISIBLE);
//                    case 1:
//                        iBtn_back.setVisibility(View.INVISIBLE);
//                    case 2:
//                        iBtn_back.setVisibility(View.INVISIBLE);
//                    default:
//                        iBtn_back.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        iBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

    }


}
