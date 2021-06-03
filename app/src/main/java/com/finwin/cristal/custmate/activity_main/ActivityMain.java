package com.finwin.cristal.custmate.activity_main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.finwin.cristal.custmate.R;

import com.finwin.cristal.custmate.databinding.ActivityMainBinding;
import com.finwin.cristal.custmate.e_shopping.ContainerFragment;
import com.finwin.cristal.custmate.home.home_fragment.HomeFragment;
import com.finwin.cristal.custmate.my_account.account.FragmentAccount;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class ActivityMain extends AppCompatActivity {


    private static FloatingTextButton flBtnMiniStmnt;
    private static FloatingTextButton flBtnBlnceEnq;
    private static final int PERMISSION_REQUEST_CODE = 200;

    //RequestQueue requestQueue;
    String ReturnStatus;
    boolean doubleBackToExitPressedOnce = false;
    ActivityMainViewmodel viewmodel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewmodel = new ViewModelProvider(this).get(ActivityMainViewmodel.class);
        binding.setViewmodel(viewmodel);
        viewmodel.setBinding(binding);
        viewmodel.setHome();
        viewmodel.getmAction().setValue("home");
        if (!checkPermission())
        {
            requestPermission();
        }

        viewmodel.getmAction().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "home":
                        viewmodel.setHome();
                        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                        if (f instanceof HomeFragment) {
                        }else {
                            HomeFragment containerFragment = new HomeFragment();
                            String backStateName = containerFragment.getClass().getName();
                            FragmentManager manager = getSupportFragmentManager();
                            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
                            if (!fragmentPopped) {
                                //fragment not in back stack, create it.
                                FragmentTransaction ft = manager.beginTransaction();
                                ft.replace(R.id.frame_layout, containerFragment, "tagHomeFragment");
                                ft.addToBackStack(backStateName);
                                ft.commit();
                            }
                        }
                        break;

                    case "scan":

                        if (checkPermission()) {
                            viewmodel.setScan();
                            //main logic or main code
                            // . write your main code to execute, It will execute if the permission is already given.
                            ContainerFragment fr = new ContainerFragment();
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_layout, fr);
                            fragmentTransaction.commit();
                        } else {
                            requestPermission();
                        }

                        break;

                    case "profile":
                        viewmodel.setProfile();
                        Fragment fr = new FragmentAccount();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fr, "account_fragment");
                        fragmentTransaction.commit();
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (viewmodel.selectedFragment.get().equals("home")) {

            Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
            if (f instanceof HomeFragment) {
                if (doubleBackToExitPressedOnce) {
                    finish();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

            }else {
                super.onBackPressed();
            }

        } else if (viewmodel.selectedFragment.get().equals("profile") || viewmodel.selectedFragment.get().equals("scan")){
            viewmodel.setHome();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            HomeFragment containerFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.frame_layout, containerFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else {
            super.onBackPressed();
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(ActivityMain.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(ActivityMain.this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // for each permission check if the user grantet/denied them
                // you may want to group the rationale in a single dialog,
                // this is just an example
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = false;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            showRationale = shouldShowRequestPermissionRationale(permission);
                        }
                        if (!showRationale) {
                            showMessageRequestDenied("Go to settings and allow permissions.\nSettings > permission > camera",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.e("onRequest onClick: ", String.valueOf(which));

                                            if (which == -1) {
                                                Intent intent = new Intent();
                                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                                intent.setData(uri);
                                                startActivity(intent);
                                            } else if (which == -2) {
                                                //mBottomBar.selectTabAtPosition(0);
                                            }
                                        }
                                    });

                        } else if (Manifest.permission.CAMERA.equals(permission)) {
//                            showRationale(permission, R.string.permission_denied_contacts);

                            showMessageRequest("You need to allow access permissions.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.e("onRequest onClick: ", String.valueOf(which));

                                            if (which == -1) {
                                                requestPermission();
                                            } else if (which == -2) {
                                                // mBottomBar.selectTabAtPosition(0);
                                            }
                                        }
                                    });

                        }

                    }
                }

                break;
        }
    }

    private void showMessageRequest(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ActivityMain.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void showMessageRequestDenied(String message, DialogInterface.OnClickListener
            okListener) {
        new AlertDialog.Builder(ActivityMain.this)
                .setMessage(message)
                .setPositiveButton("Settings", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

}
