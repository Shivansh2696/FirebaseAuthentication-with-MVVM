package com.shivansh.firebasepractice.views.activities.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.shivansh.firebasepractice.R
import com.shivansh.firebasepractice.databinding.ActivityLoginBinding
import com.shivansh.firebasepractice.views.activities.HomeActivity
import com.shivansh.firebasepractice.views.fragments.logInFragments.LogInFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var logInFragment : LogInFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        navController = findNavController(R.id.mainFrame)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController,appBarConfiguration)


    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}