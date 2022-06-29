package com.shivansh.firebasepractice.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.shivansh.firebasepractice.R
import com.shivansh.firebasepractice.databinding.ActivityHomeAcitivityBinding
import com.shivansh.firebasepractice.views.activities.authentication.LoginActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeAcitivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAcitivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            viewModelStore.clear()
            finish()
        }
    }
}