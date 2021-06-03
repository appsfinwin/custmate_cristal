package com.finwin.cristal.custmate.SupportingClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.finwin.cristal.custmate.BuildConfig;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Random;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class Enc_Utils {
    static Random rnd = new Random();
    final static Enc_crypter encr = new Enc_crypter();

    public static String decValues(String JWTEncoded) throws Exception {
        String body = "";
        try {
            String[] split = JWTEncoded.split("\\.");
//            Log.e("JWT_DECODED", "Header: " + getJson(split[0]));
//            Log.e("JWT_DECODED", "Body: " + getJson(split[1]));
//            Log.e("JWT_DECODED", "sig: " + getJson(split[2]));
            body = getJson(split[1]);
        } catch (UnsupportedEncodingException e) {
            //Error
        }
        return body;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    public static String enValues(Map hashMap) {
        Gson gson = new Gson();
        Enc_crypter encr = new Enc_crypter();
        String conkey = encr.decrypter(BuildConfig.AP_SR);
        byte[] bSig = conkey.getBytes();

        return Jwts.builder()
//                .setSubject(gson.toJson(hashMap))
                .setPayload(gson.toJson(hashMap))
                .signWith(SignatureAlgorithm.HS256, bSig)
                .compact();
    }

    public static Boolean valKey(String key, String compactJws) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws);
            //OK, we can trust this JWT//
            return true;
        } catch (SignatureException e) {
            //don't trust the JWT!
            return false;
        }
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public static String apRtv(Context context) {
        String ss = getPrefs(context).getString("items", "no");
        return encr.revDecString(encr.decrypter(ss.substring(311, 1085)));
    }

    public static void apSav(Context context, String input) {
        String f1 = "14203109201107209203201107209203210120103303103109215109201109222224214112103109202108102208210112225223218306209207222305101224214301209219209214203109201107209203202310204217115107209208301214203114301414104126206119220310415211226119205415209309120215202107302205215205308205113214307301301301304306123115103";
        String f2 = "302412911802902703902912522103602702622901102302902701102901302412911802902703902901301211412422401901102302902112103321501414013012302222422611111902703902302301603812411902911902901412203902621122902012901512901301702201801012";
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("items", f1 + encr.ConvertString(input)+ f2 + (100000 + rnd.nextInt(900000)));
        editor.commit();
//        editor.apply();
    }

}
