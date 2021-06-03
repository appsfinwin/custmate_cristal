package com.finwin.cristal.custmate.home.transfer_main;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.databinding.FrgFundTransferMainBinding;
import com.finwin.cristal.custmate.home.neftImps.neft.NeftImpsFragment;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.FundTransferAcc;
import com.finwin.cristal.custmate.home.transfer.add_beneficiary.FundTranAddBeneficiary;
import com.finwin.cristal.custmate.home.transfer.view_recent_transfers.RecentTransfersActivity;
import com.finwin.cristal.custmate.home.transfer_main.action.TransferAction;

public class FundTransferMain extends Fragment {

    FrgFundTransferMainBinding binding;
    TransferViewmodel viewmodel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.frg_fund_transfer_main, container, false);
        viewmodel = new ViewModelProvider(getActivity()).get(TransferViewmodel.class);
        binding.setViewmodel(viewmodel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel.getmAction().observe(getViewLifecycleOwner(), new Observer<TransferAction>() {
            @Override
            public void onChanged(TransferAction transferAction) {
                switch (transferAction.getAction()) {
                    case TransferAction.CLICK_BACK:
                        assert getFragmentManager() != null;
                        getFragmentManager().popBackStack();
                        break;

                    case TransferAction.ACCOUNT_TRANSFER:
                        FundTransferAcc nextFrag = new FundTransferAcc();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout, nextFrag, "FundTransferFragment")
                                .addToBackStack(null)
                                .commit();
                        break;

                    case TransferAction.NEFT_IMPS:
                        NeftImpsFragment nextFragment = NeftImpsFragment.newInstance("FirstFragment, Instance 1");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout, nextFragment, "FundTransferFragment")
                                .addToBackStack(null)
                                .commit();
                        break;

                    case TransferAction.VIEW_RECENT_TRANSFERS:
                        Intent intent = new Intent(getActivity(), RecentTransfersActivity.class);
                        getActivity().startActivity(intent);
                        break;

                    case TransferAction.ADD_BENEFICIARY:
                        FundTranAddBeneficiary nextFragt = new FundTranAddBeneficiary();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout, nextFragt, "FundTransferFragment")
                                .addToBackStack(null)
                                .commit();
                        break;
                    default:

                        break;
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        viewmodel.getmAction().setValue(new TransferAction(TransferAction.DEFAULT));
    }
}