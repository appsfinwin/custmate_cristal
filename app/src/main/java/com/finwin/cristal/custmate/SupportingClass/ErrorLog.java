package com.finwin.cristal.custmate.SupportingClass;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class ErrorLog {

    private final static String api_url_error = "ip_global" + "/Error_log";
    final static Enc_crypter encr = new Enc_crypter();
    static RequestQueue queue;

    public static void submitError(Context getcontext, final String erClass, final String error) {

        try {
//=====================================================================================================
            Log.e("getcontext =", getcontext.toString());
            Log.e("erClass =", erClass);
            Log.e("error =", error);
//        ErrorLog.submitError(getActivity(),this.getClass().getSimpleName(),"ayyoooo");
//        getActivity().getApplicationContext()
////=====================================================================================================

            queue = Volley.newRequestQueue(getcontext);
            StringRequest postRequest = new StringRequest(Request.Method.POST, api_url_error,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.e("JSONResult error : ", response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("ERROR", "error => " + error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    Map<String, String> items = new HashMap<>();
                    items.put("device", getDevice());
                    items.put("version", Build.VERSION.RELEASE);
                    items.put("api_level", String.valueOf(Build.VERSION.SDK_INT));
                    items.put("error_class", erClass);
                    items.put("error", error);

                    params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
//                Log.e("error post", encr.conRevString(Enc_Utils.enValues(items)));
                    return params;

                }
            };
            queue.add(postRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getDevice() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
//        int ApiLevel = Build.VERSION.SDK_INT;
//        String versionRelease = Build.VERSION.RELEASE;
//        Log.e("getDevice", manufacturer + "-" + model + ", Android:" + versionRelease);
        return manufacturer + "-" + model;
    }

}
