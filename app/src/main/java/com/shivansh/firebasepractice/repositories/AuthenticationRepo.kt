package com.shivansh.firebasepractice.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.shivansh.firebasepractice.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object AuthenticationRepo {
    private val signUpLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val LogInLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        signUpLiveData.value = false
        LogInLiveData.value = false
    }

    fun insertUser(user : User) : LiveData<Boolean>{
        CoroutineScope(Dispatchers.IO).launch {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email,user.password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(user).addOnCompleteListener {
                                CoroutineScope(Dispatchers.Main).launch {
                                    if (it.isSuccessful) {
                                        signUpLiveData.value = true
                                    }
                                }
                            }
                    }
                }
        }
        return signUpLiveData
    }

    fun logInUser(email : String, password : String): LiveData<Boolean> {

        if (FirebaseAuth.getInstance().currentUser == null){
            CoroutineScope(Dispatchers.IO).launch {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{ task : Task<AuthResult> ->
                        if (task.isSuccessful){
                            val firebaseUser = FirebaseAuth.getInstance().currentUser
                            CoroutineScope(Dispatchers.Main).launch {
                                if(firebaseUser != null){
                                    LogInLiveData.value = true
                                }
                            }
                        }
                    }
            }
        }

        return LogInLiveData
    }
}