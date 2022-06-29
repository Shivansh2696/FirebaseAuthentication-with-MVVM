package com.shivansh.firebasepractice.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.shivansh.firebasepractice.utils.Valid
import java.util.concurrent.TimeUnit

class PhoneAuthViewmodel() :  ViewModel() {

    val phone : MutableLiveData<String> = MutableLiveData()
    var phoneError : MutableLiveData<String> = MutableLiveData()
    var onLoginClickResponse : MutableLiveData<Boolean> = MutableLiveData()
    var otp : MutableLiveData<String> = MutableLiveData()
    var otpError : MutableLiveData<String> = MutableLiveData()
    var credential: PhoneAuthCredential?=null
    var verificationId:String?=null
    val singingListener:MutableLiveData<Boolean>  =MutableLiveData()
    val codeSentListener:MutableLiveData<Boolean>  =MutableLiveData()

    init {
        onLoginClickResponse.value = false
        codeSentListener.value=false
        phone.value="8917017464"
        otp.value="111111"

    }

    fun onClickGetOTP(){
        onLoginClickResponse.value = validate()
    }

    private fun validate(): Boolean {
        val isValidPhone = Valid.isValidPhone(phone.value.toString().trim())

        if (!isValidPhone){
            phoneError.value = "Please Enter Valid Mobile Number"
        }

        return isValidPhone
    }

    fun onClickSubmitOtp(){
        if (validateOtp()){
            val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationId!!,otp.value!!)
            signInWithPhoneAuthCredential(credential)

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{ task ->
            singingListener.value=task.isSuccessful
            if (!task.isSuccessful){
                task.exception!!.printStackTrace()
            }
        }
    }

    private fun validateOtp(): Boolean {
        val isValidOTP = Valid.isValidOtp(otp.value.toString())
        println(isValidOTP)
        if (!isValidOTP){
            otpError.value = "Please Enter Valid OTP"
        }
        return isValidOTP
    }
    val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks= object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(cred: PhoneAuthCredential) {
            credential=cred
        }
        override fun onVerificationFailed(p0: FirebaseException) {}

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId,token)
            this@PhoneAuthViewmodel.verificationId=verificationId
            codeSentListener.value=true
        }
    }
}