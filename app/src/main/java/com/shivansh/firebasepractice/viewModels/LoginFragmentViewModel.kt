package com.shivansh.firebasepractice.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.officetask.models.ErrorModel
import com.shivansh.firebasepractice.repositories.AuthenticationRepo
import com.shivansh.firebasepractice.utils.Valid
import kotlinx.coroutines.*

class LoginFragmentViewModel : ViewModel() {

    var email : MutableLiveData<String> =  MutableLiveData()
    var password : MutableLiveData<String> = MutableLiveData()
    val errorModel : MutableLiveData<ErrorModel> = MutableLiveData()

    val onSignUpResponse : MutableLiveData<Boolean> = MutableLiveData()
    val onLoginUserResponse : MutableLiveData<Boolean> = MutableLiveData()

    init {
        errorModel.value = ErrorModel()
        onSignUpResponse.value = false
        onLoginUserResponse.value = false
    }

    fun onClickLogin(){
        if (validate()){
            loginUser()
        }
    }

    fun loginUser() {
        onLoginUserResponse.value = AuthenticationRepo.logInUser(email.value.toString(),password.value.toString()).value
    }

    fun onClickSignUp(){
        onSignUpResponse.value = true
    }

    private fun validate() : Boolean{
        val error = ErrorModel()
        val isValidEmail = Valid.isValidEmail(email.value.toString().trim())
        val isValidPassword = Valid.isValidPassword(password.value.toString().trim())

        if(!isValidEmail){
            error.emailErrorMessage = "Please Enter Valid Email"
        }
        if(!isValidPassword){
            error.passwordErrorMessage = "Please Enter Valid Password"
        }
        errorModel.value = error

        return  isValidEmail && isValidPassword
    }

}