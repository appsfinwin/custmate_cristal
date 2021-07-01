package com.finwin.cristal.custmate.home.reacharge.otp_validation

import androidx.lifecycle.MutableLiveData
import com.finwin.cristal.custmate.SupportingClass.Enc_Utils
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter
import com.finwin.cristal.custmate.home.reacharge.action.RechargeAction
import com.finwin.cristal.custmate.home.reacharge.otp_validation.action.OtpAction
import com.finwin.cristal.custmate.home.reacharge.pojo.RechargeResponse
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.genarate_otp.GenarateOtpResponse
import com.finwin.cristal.custmate.pojo.Response
import com.finwin.cristal.custmate.retrofit.ApiInterface
import com.google.gson.GsonBuilder
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class OtpValidationRepository {
    lateinit var INSTANCE: OtpValidationRepository
    var mAction: MutableLiveData<OtpAction>? = null
    var encr = Enc_crypter()

    public fun getInstance () : OtpValidationRepository {
        if (!::INSTANCE.isInitialized) {
            INSTANCE= OtpValidationRepository()
        }
        return INSTANCE
    }

//    fun generateOtp(apiInterface: ApiInterface, body: RequestBody?) {
//        val call = apiInterface.generateOtp(body)
//        call.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : SingleObserver<Response> {
//                    override fun onSubscribe(d: Disposable) {
//                        //compositeDisposable.add(d)
//                    }
//
//                    override fun onSuccess(response: Response) {
//                        try {
//                            var data = Enc_Utils.decValues(encr.revDecString(response.data))
//                            data = Enc_Utils.decValues(encr.revDecString(response.data))
//                            val gson = GsonBuilder().create()
//                            val genarateOtpResponse = gson.fromJson(data, GenarateOtpResponse::class.java)
//                            if (genarateOtpResponse.otp != null) {
//                                mAction?.setValue(OtpAction(OtpAction.GENERATE_OTP_SUCCESS, genarateOtpResponse))
//                            } else {
//                                val error = genarateOtpResponse.error
//                                mAction?.setValue(OtpAction(OtpAction.API_ERROR, error))
//                            }
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                        }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        if (e is SocketTimeoutException) {
//                            mAction?.setValue(OtpAction(OtpAction.API_ERROR, "Timeout! Please try again later"))
//                        } else if (e is UnknownHostException) {
//                            mAction?.setValue(OtpAction(OtpAction.API_ERROR, "No Internet"))
//                        } else {
//                            mAction?.setValue(e.message?.let { OtpAction(OtpAction.API_ERROR, it) })
//                        }
//                    }
//                })
//    }

    fun resendOtp(apiInterface: ApiInterface, body: RequestBody?) {
        val call = apiInterface.generateOtp(body)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response> {
                    override fun onSubscribe(d: Disposable) {
                        //compositeDisposable.add(d)
                    }

                    override fun onSuccess(response: Response) {
                        try {
                            var data = Enc_Utils.decValues(encr.revDecString(response.data))
                            data = Enc_Utils.decValues(encr.revDecString(response.data))
                            val gson = GsonBuilder().create()
                            val genarateOtpResponse = gson.fromJson(data, GenarateOtpResponse::class.java)
                            if (genarateOtpResponse.otp != null) {
                                mAction?.value=(OtpAction(OtpAction.RESEND_OTP_SUCCESS, genarateOtpResponse))
                            } else {
                                val error = genarateOtpResponse.error
                                mAction?.value=(OtpAction(OtpAction.API_ERROR, error))
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (e is SocketTimeoutException) {
                            mAction?.value=(OtpAction(OtpAction.API_ERROR, "Timeout! Please try again later"))
                        } else if (e is UnknownHostException) {
                            mAction?.value=(OtpAction(OtpAction.API_ERROR, "No Internet"))
                        } else {
                            mAction?.value=(e.message?.let { OtpAction(OtpAction.API_ERROR, it) })
                        }
                    }
                })
    }

    fun recharge(apiInterface: ApiInterface, body: RequestBody?) {
        val call = apiInterface.recharge(body)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response> {
                    override fun onSubscribe(d: Disposable) {
                        //disposable.add(d)
                    }

                    override fun onSuccess(response: Response) {
                        try {
                            var data = Enc_Utils.decValues(encr.revDecString(response.data))
                            data = Enc_Utils.decValues(encr.revDecString(response.data))
                            val gson = GsonBuilder().create()
                            val rechargeResponse = gson.fromJson(data, RechargeResponse::class.java)
                            if (rechargeResponse.status!=null){
                            if ( rechargeResponse.status == "1") {
                                mAction!!.value=(OtpAction(OtpAction.RECHARGE_SUCCESS, rechargeResponse))
                            }else  if ( rechargeResponse.status == "0") {
                                mAction!!.value = (OtpAction(OtpAction.RECHARGE_ERROR, rechargeResponse.msg))
                            }
                            }else {
                                if (rechargeResponse.msg== "Error!" || rechargeResponse.msg== "Failed!") {
                                    mAction!!.value=(OtpAction(OtpAction.RECHARGE_ERROR, rechargeResponse .msg))
                                }
                            }
                        } catch (e: java.lang.Exception) {
                            mAction!!.value=(OtpAction(OtpAction.RECHARGE_ERROR, e.localizedMessage))
                            e.printStackTrace()
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (e is SocketTimeoutException) {
                            mAction!!.value=(OtpAction(OtpAction.API_ERROR, "Timeout! Please try again later"))
                        } else if (e is UnknownHostException) {
                            mAction!!.value=(OtpAction(OtpAction.API_ERROR, "No Internet"))
                        } else {
                            mAction!!.value=(e.message?.let { OtpAction(OtpAction.API_ERROR, it) })
                        }
                    }
                })
    }
}