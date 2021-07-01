package com.finwin.cristal.custmate.home.reacharge.otp_validation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.cristal.custmate.SupportingClass.ConstantClass
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter
import com.finwin.cristal.custmate.home.reacharge.otp_validation.action.OtpAction
import com.finwin.cristal.custmate.retrofit.ApiInterface
import com.finwin.cristal.custmate.retrofit.RetrofitClient
import com.finwin.cristal.custmate.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class OtpValidationViewModel(application: Application) : AndroidViewModel(application) {
    var repository = OtpValidationRepository().getInstance()
    var etOtp = ObservableField("")
    var encr = Enc_crypter()
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var amount= ObservableField("")

    var account_no=ObservableField("")
    var agent_id =ObservableField("")
    var circle =ObservableField("")
    var customer_id =ObservableField("")
    var Operator =ObservableField("")
    var mobile =ObservableField("")
    var recharge_type =ObservableField("")
    var otp_id =ObservableField("")

    var mAction: MutableLiveData<OtpAction> = MutableLiveData()
    init {
        sharedPreferences = application.getSharedPreferences("com.finwin.cristal.custmate", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        repository.mAction=mAction
    }

    var loading: SweetAlertDialog? = null

    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context);
        loading?.setCancelable(false)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }

    fun resendOtp() {

        //viewmodel.recharge();
        val params: MutableMap<String, Any?> = HashMap()
        val items: MutableMap<String?, String?> = HashMap()
        items["particular"] = "mob"
        items["account_no"] = sharedPreferences?.getString(ConstantClass.accountNumber, "")
        items["amount"] = amount.get()
        items["agent_id"] = sharedPreferences?.getString(ConstantClass.custId, "")
        params["data"] = encr.conRevString(Enc_Utils.enValues(items))
        var apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface::class.java)
        val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                JSONObject(params).toString()
        )

        repository.resendOtp(apiInterface, body)

    }

    fun clickRecharge(view: View)
    {
        if (etOtp.get()=="")
        {
            Toast.makeText(view.context,"Please enter OTP",Toast.LENGTH_SHORT).show()
        }else{
            initLoading(view.context)
            recharge()
        }
    } fun clickResendOtp(view: View)
    {
        initLoading(view.context)
        etOtp.set("`")
        resendOtp()
    }

    fun recharge() {

        val params: MutableMap<String, Any?> = HashMap()
        val items: MutableMap<String, Any?> = HashMap()

        items.put("account_no", account_no.get())
        items.put("agent_id", "0")
        items.put("amount", amount.get())
        items.put("circle", circle.get())
        items.put("customer_id",
                sharedPreferences!!.getString(ConstantClass.custId, ""))
        items.put("mobile", mobile.get())
        items.put("Operator", Operator.get())
        items.put("recharge_type", recharge_type.get())
        items.put("otp_id", otp_id.get())
        items.put("otp_val", etOtp.get())
        params["data"] = encr.conRevString(Enc_Utils.enValues(items))
        var request=  JSONObject(items).toString()
         request=  JSONObject(items).toString()

        var requestEncrypted=  JSONObject(params).toString()
         requestEncrypted=  JSONObject(params).toString()
        var apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface::class.java)
        val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                JSONObject(params).toString()
        )
        repository.recharge(apiInterface, body)
    }
}