package com.finwin.cristal.custmate.AdapterAndModel;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.finwin.cristal.custmate.home.loan.LoanFirstFragment;
import com.finwin.cristal.custmate.home.loan.LoanSecondFragment;
import com.finwin.cristal.custmate.home.loan.LoanThirdFragment;

public class LoanViewPageAdapter extends FragmentStatePagerAdapter {

    public LoanViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return LoanFirstFragment.newInstance("LoanFirstFragment, Instance 1");
            case 1:
                return LoanSecondFragment.newInstance("LoanSecondFragment, Instance 1");
            case 2:
                return LoanThirdFragment.newInstance("LoanThirdFragment, Instance 1");
            default:
                return LoanFirstFragment.newInstance("LoanFirstFragment, Default");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}