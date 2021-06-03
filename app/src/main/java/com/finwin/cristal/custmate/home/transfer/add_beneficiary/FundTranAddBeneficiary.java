package com.finwin.cristal.custmate.home.transfer.add_beneficiary;

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
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.databinding.FrgAddBeneficiaryBinding;
import com.finwin.cristal.custmate.home.transfer.add_beneficiary.action.AddBeneficiaryAction;
import com.finwin.cristal.custmate.home.transfer.view_beneficiary_list.BeneficiaryListActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.view.View.VISIBLE;

public class FundTranAddBeneficiary extends Fragment {

    Enc_crypter encr = new Enc_crypter();
    SweetAlertDialog dialog;
    AddBeneficiaryViewmodel viewmodel;
    FrgAddBeneficiaryBinding binding;
    String benType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_add_beneficiary, container, false);
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);

        viewmodel = new ViewModelProvider(getActivity()).get(AddBeneficiaryViewmodel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.frg_add_beneficiary, container, false);
        binding.setViewmodel(viewmodel);
        viewmodel.setBinding(binding);

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewmodel.mAction.setValue(new AddBeneficiaryAction(AddBeneficiaryAction.DEFAULT));
        viewmodel.clearData();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel.getObBenType().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                benType=s;
            }
        });

        viewmodel.getmAction().observe(getViewLifecycleOwner(), new Observer<AddBeneficiaryAction>() {
            @Override
            public void onChanged(AddBeneficiaryAction addBeneficiaryAction) {
                viewmodel.cancelLoading();
                switch (addBeneficiaryAction.getAction()) {
                    case AddBeneficiaryAction.GET_ACCOUNT_HOLDER_SUCCESS:
                        binding.tvVarify.setText("Verified");
                        binding.imgVarify.setImageResource(R.drawable.ic_verified_true);
                        viewmodel.isAccountVarified.set(true);
                        break;

                    case AddBeneficiaryAction.GET_IFSC_SUCCESS:
                        viewmodel.setBankData(addBeneficiaryAction.getGetIfscResponse());
                        binding.tvIfscVerify.setText("Verified");
                        binding.imgIfscVerify.setImageResource(R.drawable.ic_verified_true);
                        viewmodel.bankDetailsVisibility.set(VISIBLE);
                        break;

                    case AddBeneficiaryAction.GET_IFSC_ERROR:
                        viewmodel.isIfscVarified.set(false);
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ERROR")
                                .setContentText("Invalid IFSC")
                                .show();
                        break;

                    case AddBeneficiaryAction.GET_ACCOUNT_HOLDER_ERROR:
                        viewmodel.isAccountVarified.set(false);
                        binding.tvIfscVerify.setText("Verify");
                        binding.imgIfscVerify.setImageResource(R.drawable.ic_verified_false);
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ERROR")
                                .setContentText(addBeneficiaryAction.getError())
                                .show();
                        break;

                    case AddBeneficiaryAction.API_ERROR:

                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ERROR")
                                .setContentText(addBeneficiaryAction.getError())
                                .show();
                        break;

                    case AddBeneficiaryAction.ADD_BENEFICIARY_SUCCESS:
                        viewmodel.clearData();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("SUCCESS")
                                .setContentText("Beneficiary Added")
                                .show();
                        break;

                    case AddBeneficiaryAction.CLICK_BACK:

                        assert getFragmentManager() != null;
                        getFragmentManager().popBackStack();
                        break;

                    case AddBeneficiaryAction.CLICK_BENEFICIARY_LIST:
                        Intent intent = new Intent(getActivity(), BeneficiaryListActivity.class);
                        intent.putExtra("benType",benType);
                        getActivity().startActivity(intent);
                        break;
                }
            }
        });
    }



}
