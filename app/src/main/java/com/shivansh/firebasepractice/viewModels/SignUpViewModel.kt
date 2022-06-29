package com.shivansh.firebasepractice.viewModels

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.officetask.models.ErrorModel
import com.shivansh.firebasepractice.models.User
import com.shivansh.firebasepractice.repositories.AuthenticationRepo
import com.shivansh.firebasepractice.utils.Valid

class SignUpViewModel : ViewModel() {

    var email : MutableLiveData<String> = MutableLiveData()
    var name : MutableLiveData<String> = MutableLiveData()
    var password : MutableLiveData<String> = MutableLiveData()
    var mobile : MutableLiveData<String> = MutableLiveData()

    var userError : MutableLiveData<ErrorModel> = MutableLiveData()

    val signUpClickResponse : MutableLiveData<Boolean> = MutableLiveData()
    val onClickImageResponse : MutableLiveData<Boolean> = MutableLiveData()

    init {
        userError.value = ErrorModel()
        signUpClickResponse.value =false
        onClickImageResponse.value = false
    }

    fun onClickSignUp(){
        signUpClickResponse.value = validate()
    }

    fun onClickImage(){
        onClickImageResponse.value = true
    }

    fun insertUser(user : User) : LiveData<Boolean>{
       return AuthenticationRepo.insertUser(user)
    }

    private fun validate() : Boolean{
        val errorModel  = ErrorModel()

        val isValidName = TextUtils.isEmpty(name.value.toString().trim())
        val isValidEmail = Valid.isValidEmail(email.value.toString().trim())
        val isValidPassword = Valid.isValidPassword(password.value.toString().trim())
        val isValidPhone = Valid.isValidPhone(mobile.value.toString().trim())

        if(isValidName){
            errorModel.nameErrorMessage = "Please Enter Name Here"
        }

        if(!isValidEmail){
            errorModel.emailErrorMessage = "Please Enter Valid Email"
        }
        if(!isValidPassword){
            errorModel.passwordErrorMessage = "Please Enter Valid Password"
        }
        if(!isValidPhone){
            errorModel.phoneErrorMessage = "Please Enter Valid Phone Number"
        }

        userError.value = errorModel
        return !isValidName && isValidEmail && isValidPassword && isValidPhone
    }
}