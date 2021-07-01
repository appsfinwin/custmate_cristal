package com.finwin.cristal.custmate.home.reacharge.otp_validation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.cristal.custmate.R
import com.finwin.cristal.custmate.SupportingClass.Enc_crypter
import com.finwin.cristal.custmate.databinding.ActivityOtpValidationBinding
import com.finwin.cristal.custmate.home.reacharge.RechargeFragmentReceipt
import com.finwin.cristal.custmate.home.reacharge.otp_validation.action.OtpAction
import java.security.AccessController.getContext

class OtpValidationActivity : AppCompatActivity() {
    var doubleBackToExitPressedOnce = false
    lateinit var binding:ActivityOtpValidationBinding
    lateinit var viewModel: OtpValidationViewModel
    val encr = Enc_crypter()
    var account_no:String=""
    var agent_id:String=""
    var amount:String=""
    var circle:String=""
    var customer_id:String=""
    var Operator:String=""
    var mobile:String=""
    var recharge_type:String=""
    var otp_id:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_otp_validation)
        viewModel= ViewModelProvider(this)[OtpValidationViewModel::class.java]
        binding.viewModel=viewModel
        var intent=intent



            account_no= intent.getStringExtra("account_no")
            viewModel.account_no.set(account_no)
            viewModel.agent_id.set(intent.getStringExtra("agent_id"))
            viewModel.amount.set(intent.getStringExtra("amount"))

            viewModel.circle.set(intent.getStringExtra("circle"))
            viewModel.customer_id.set(intent.getStringExtra("customer_id"))
            viewModel.Operator.set(intent.getStringExtra("Operator"))
            viewModel.mobile.set(intent.getStringExtra("mobile"))
            viewModel.recharge_type.set(intent.getStringExtra("recharge_type"))
            viewModel.otp_id.set(intent.getStringExtra("otp_id"))


        viewModel.mAction.observe(this, androidx.lifecycle.Observer {
            viewModel.cancelLoading()
            when(it.action)
            {
                OtpAction.API_ERROR->{
                    SweetAlertDialog(this@OtpValidationActivity, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setContentText(it.error)
//                            .setConfirmClickListener { sweetAlertDialog ->
//                                finish()
//                                sweetAlertDialog.cancel()
//                            }
                            .show()
                }
                OtpAction.RECHARGE_SUCCESS->{
                    val intent = Intent(this@OtpValidationActivity, RechargeFragmentReceipt::class.java)
                    intent.putExtra("account_number", viewModel.mobile.get())
                    intent.putExtra("Rech_Amount", viewModel.amount.get())
                    startActivity(intent)
                }
                OtpAction.RECHARGE_ERROR->{
                    val customView = LayoutInflater.from(applicationContext).inflate(R.layout.layout_error_layout, null)
                    val tv_error = customView.findViewById<TextView>(R.id.tv_error)
                    tv_error.setText(it.error)
                    SweetAlertDialog(this@OtpValidationActivity, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setCustomView(customView)
//                            .setConfirmClickListener { sweetAlertDialog ->
//                                finish()
//                                sweetAlertDialog.cancel()
//                            }
                            .show()


//                    val sdcard: File = Environment.getExternalStorageDirectory()
//                    val dir = File(sdcard.getAbsolutePath().toString() + "/cristal/")
//                    dir.mkdir()
//                    val file = File(dir, "input.txt")
//                    var os: FileOutputStream? = null
//                    try {
//                        os = FileOutputStream(file)
//                        os.write(enterText.getText().toString().getBytes())
//                        os.close()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
                }
            }
        })
    }


    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
//            editor.putString("success", "no")
//            editor.commit()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Click again to cancel the payment", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }


}