package com.finwin.cristal.custmate.e_shopping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter;
import com.finwin.cristal.custmate.SupportingClass.ErrorLog;
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.FundTransferAcc;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.scottyab.aescrypt.AESCrypt;
import com.suke.widget.SwitchButton;

import java.security.GeneralSecurityException;

/**
 * Created by AnVin on 1/9/2017.
 */
@SuppressLint("ValidFragment")
public class E_Shopping extends Fragment implements QRCodeReaderView.OnQRCodeReadListener {
    private int mPosition;
    static QRCodeReaderView qrCodeReaderView;
    Context thisContext;
    String encryptedMsg = "", mobile_number;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public E_Shopping(int position) {
        mPosition = position;
    }

    final Enc_crypter encr = new Enc_crypter();
    String QRcontent, QRpassword;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        sharedPreferences = getActivity().getSharedPreferences("com.finwin.cristal.custmate", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (mPosition == 20) {
            View e_shopping_view = inflater.inflate(R.layout.frg_e_shopping, container, false);
            thisContext = e_shopping_view.getContext();
            Shimmer shimmer = new Shimmer();
            ShimmerTextView shimmerTextView = e_shopping_view.findViewById(R.id.qr_shimmer);
            ShimmerTextView qr_focus = e_shopping_view.findViewById(R.id.qr_shimmer_focus);
            shimmer.start(shimmerTextView);
            shimmer.start(qr_focus);
            qrCodeReaderView = new QRCodeReaderView(thisContext);
            qrCodeReaderView = (QRCodeReaderView) e_shopping_view.findViewById(R.id.qrdecoderview);
            qrCodeReaderView.setOnQRCodeReadListener(this);
            qrCodeReaderView.setQRDecodingEnabled(true);
            qrCodeReaderView.setAutofocusInterval(2L);
//            pDialogue.dismiss();
            qrCodeReaderView.setBackCamera();

            final SwitchButton switchButton = (SwitchButton) e_shopping_view.findViewById(R.id.flashToggle);
            switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    qrCodeReaderView.setTorchEnabled(true);
                    if (!isChecked) {
                        qrCodeReaderView.setTorchEnabled(false);
                    }
                }
            });

            final EditText number = e_shopping_view.findViewById(R.id.e_shopping_number_editText);
            Button mobile = e_shopping_view.findViewById(R.id.e_shopping_mobile_btn);
            mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mobile_number = number.getText().toString();
//                    new getAccountInfo().execute();
//                    getAccountInfo();
                    Log.e("onClick: ", "CLICKED...");


                    Bundle args = new Bundle();
                    args.putString("TRANS_TYP", "QR");
                    args.putString("TRANS_ACCNO", mobile_number);

                    FundTransferAcc nextFrag = new FundTransferAcc();
                    nextFrag.setArguments(args);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                            .replace(R.id.frame_layout, nextFrag, "FundTransferFragment")
                            .addToBackStack(null)
                            .commit();
                }
            });

            return e_shopping_view;
        }

        if (mPosition == 21) {
//            QRcontent = ConstantClass.const_name + "," + ConstantClass.const_accountNumber + "," + ConstantClass.const_phone;

//            QRcontent = "133509";
            QRcontent = sharedPreferences.getString(ConstantClass.accountNumber,"");
            QRpassword = ConstantClass.pinforQR;

            try {
                encryptedMsg = AESCrypt.encrypt(QRpassword, QRcontent);
            } catch (GeneralSecurityException e) {
                //handle error
            }
            View my_qr_view = inflater.inflate(R.layout.my_qr, container, false);
            QRCodeWriter writer = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = writer.encode(encryptedMsg, BarcodeFormat.QR_CODE, 512, 512);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
                ((ImageView) my_qr_view.findViewById(R.id.imageView)).setImageBitmap(bmp);

            } catch (WriterException e) {
                e.printStackTrace();
                ErrorLog.submitError(getActivity(), this.getClass().getSimpleName() + ":" + new Object() {
                }.getClass().getEnclosingMethod().getName(), e.toString());
            }
            return my_qr_view;
        }
        return rootView;

    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
//        pointsOverlayView.setPoints(points);
        qrCodeReaderView.stopCamera();
        qrCodeReaderView.setQRDecodingEnabled(false);

//        Log.e("onQRCodeRead: ", text);
//        String encryptedMsg = "2B22cS3UC5s35WBihLBo8w==";
        String msgDecrypt;
        try {
            msgDecrypt = AESCrypt.decrypt(ConstantClass.pinforQR, text);
            Log.e("msgDecrypt ", msgDecrypt);

            Bundle args = new Bundle();
            args.putString("TRANS_TYP", "QR");
            args.putString("TRANS_ACCNO", msgDecrypt);

            FundTransferAcc nextFrag = new FundTransferAcc();
            nextFrag.setArguments(args);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.frame_layout, nextFrag, "FundTransferFragment")
                    .addToBackStack(null)
                    .commit();
        } catch (GeneralSecurityException e) {
            //handle error - could be due to incorrect password or tampered encryptedMsg
            Log.e("GeneralSecurityExcep: ", String.valueOf(e));
        } catch (Exception e) {
            Log.e("Exception: ", String.valueOf(e));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
        qrCodeReaderView.setQRDecodingEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }


}

