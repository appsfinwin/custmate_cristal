package com.finwin.cristal.custmate.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.finwin.cristal.custmate.BuildConfig;
import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.account_selection_fragment.AccountSelectionFragment;
import com.finwin.cristal.custmate.databinding.ActivityLoginBinding;
import com.finwin.cristal.custmate.databinding.LayoutSiginInBinding;
import com.finwin.cristal.custmate.login.action.LoginAction;
import com.finwin.cristal.custmate.sign_up.sign_up.SignUpActivity;
import com.finwin.cristal.custmate.utils.VersionChecker;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginFragment extends Fragment {

    String  apiKeyStored = "";
    final Enc_crypter encr = new Enc_crypter();
    private ProgressBar progress;

    LoginViewmodel viewmodel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityLoginBinding binding;
    String latestVersion,currentVersion;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_login, container, false);
        viewmodel=new ViewModelProvider(getActivity()).get(LoginViewmodel.class);
        binding.setViewmodel(viewmodel);

        viewmodel.setBinding(binding);
        // FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
//        SharedPreferences sharedPreferences;
//        SharedPreferences.Editor editor;
        sharedPreferences= getActivity().getSharedPreferences("com.finwin.cristal.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

        Glide.with(getActivity()).load(ConstantClass.imageUrl+"CristalgrpLogo.jpeg")
                .placeholder(R.drawable.cristal_logo)
                .into(binding.imgLogo);

        checkVersion();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel.getmAction().observe(getViewLifecycleOwner(), new Observer<LoginAction>() {
            @Override
            public void onChanged(LoginAction loginAction) {

                // binding.progress.setVisibility(GONE);
                switch (loginAction.getAction()) {
                    case LoginAction.API_KEY_SUCCESS:
                        apiKeyStored = encr.revDecString(encr.decrypter(BuildConfig.AP_KE));
                        if (apiKeyStored.equals(loginAction.getError())) {
                            viewmodel.login();
                        }
                        break;

                    case LoginAction.CLICK_SIGNUP:
                        Intent i = new Intent(getContext(), SignUpActivity.class);
                        startActivity(i);

//                        Intent launchIntent =getActivity(). getPackageManager().getLaunchIntentForPackage("com.phonepe.app");
//                        if (launchIntent != null) {
//                            startActivity(launchIntent);
//                        } else {
//                            launchIntent = new Intent(Intent.ACTION_VIEW);
//                            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            launchIntent.setData(Uri.parse("market://details?id=" + "com.phonepe.app"));
//                            startActivity(launchIntent);
//                        }
                        break;

                    case LoginAction.API_ERROR:
                        viewmodel.cancelLoading();
                        View customView= LayoutInflater.from(getContext()).inflate(R.layout.layout_error_layout,null);
                        TextView tv_error=customView.findViewById(R.id.tv_error);
                        tv_error.setText(loginAction.getError());

                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error!")
                                //.setContentText(loginAction.getError())
                                .setCustomView(customView)
                                .show();
                        break;

                    case LoginAction.LOGIN_SUCCESS:
                        viewmodel.cancelLoading();
                        editor.putString(ConstantClass.userName, loginAction.getLoginResponse().getUser().getData().getUSERNAME());
                        editor.putString(ConstantClass.phoneNumber, loginAction.getLoginResponse().getUser().getData().getMOBILENO());
                        editor.putString(ConstantClass.custId, loginAction.getLoginResponse().getUser().getData().getUSERID());
                        editor.putBoolean(ConstantClass.mpinStatus, loginAction.getLoginResponse().getUser().getData().getMPINstatus());
                        editor.commit();

                        ConstantClass.listAccountNumbers=new ArrayList<>();
                        ConstantClass.accountList=new ArrayList<>();
                        ConstantClass.listAccountNumbers.clear();
                        ConstantClass.listScheme=new ArrayList<>();
                        ConstantClass.listScheme.clear();
                        ConstantClass.accountList.clear();


                        int size=loginAction.getLoginResponse().getUser().getData().getAccNo().size();
                        for (int j=0;j<size;j++)
                        {
                            ConstantClass.accountList.add(loginAction.getLoginResponse().getUser().getData().getAccNo().get(j));
                            // ConstantClass.listScheme.add(loginAction.getLoginResponse().getUser().getData().getAccNo().get(j).getSchname());
                        }
                        openAccountSelectionFrag();
                        break;


                }
            }
        });

    }


    private void openAccountSelectionFrag() {
        try {
            FragmentManager fragmentManager = getFragmentManager();
            Class fragmentClass = AccountSelectionFragment.class;
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.frame_layout_login, fragment).commit();

        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
//            ErrorLog.submitError(getContext(), this.getClass().getSimpleName() + ":" + new Object() {
//            }.getClass().getEnclosingMethod().getName(), e.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
//            ErrorLog.submitError(getContext(), this.getClass().getSimpleName() + ":" + new Object() {
//            }.getClass().getEnclosingMethod().getName(), e.toString());
        }
    }

    private void checkVersion() {
        VersionChecker versionChecker = new VersionChecker();
        try {

            latestVersion = versionChecker.execute().get();
            // Toast.makeText(getActivity().getApplicationContext(), latestVersion , Toast.LENGTH_SHORT).show();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        PackageManager manager = getActivity().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert info != null;
        currentVersion = info.versionName;

        if(Float.parseFloat(currentVersion)<Float.parseFloat(latestVersion)){
            showUpdateDialog();
        }else {

        }
    }

    private void showUpdateDialog() {

        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(getActivity());
        // Setting Dialog Title
        alertDialog2.setTitle("Update Available");
        // Setting Dialog Message
        alertDialog2.setMessage("There is a newer version of this application is available");
        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.finwin.cristal.custmate"));
                        startActivity(i);
                    }
                });
        alertDialog2.setCancelable(false);
        alertDialog2.show();

    }

    private void setloginStatus(String user, String pass) {
        try {
            SharedPreferences.Editor editor = Objects.requireNonNull(getContext()).getSharedPreferences(ConstantClass.PREFS_DATA, Context.MODE_PRIVATE).edit();
            editor.putBoolean("login", true);
            editor.putString("ue", user);
            editor.putString("pd", pass);
            editor.apply();
        } catch (Exception ignored) {

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        checkVersion();
    }

}
