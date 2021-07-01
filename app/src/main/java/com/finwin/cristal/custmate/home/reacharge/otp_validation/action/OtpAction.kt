package com.finwin.cristal.custmate.home.reacharge.otp_validation.action

import com.finwin.cristal.custmate.home.reacharge.pojo.RechargeResponse
import com.finwin.cristal.custmate.home.transfer.fund_transfer_account.pojo.genarate_otp.GenarateOtpResponse

class OtpAction {
    companion object{
        const val DEFAULT = -1
        const val API_ERROR = 1
        const val RESEND_OTP_SUCCESS = 2
        const val RECHARGE_SUCCESS = 3
        const val RECHARGE_ERROR = 4
    }

    var action: Int = 0
    lateinit var error: String
    lateinit var getOtpResponse: GenarateOtpResponse
    lateinit var rechargeResponse: RechargeResponse



    constructor(action: Int, getOtpResponse: GenarateOtpResponse) {
        this.action = action
        this.getOtpResponse = getOtpResponse
    }

    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int, rechargeResponse: RechargeResponse) {
        this.action = action
        this.rechargeResponse = rechargeResponse
    }


}