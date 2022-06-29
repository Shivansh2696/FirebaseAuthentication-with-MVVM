package com.shivansh.firebasepractice.views.fragments.logInFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.shivansh.firebasepractice.R
import com.shivansh.firebasepractice.databinding.FragmentPhoneLogInBinding
import com.shivansh.firebasepractice.viewModels.PhoneAuthViewmodel
import com.shivansh.firebasepractice.views.activities.HomeActivity
import java.util.concurrent.TimeUnit

class PhoneLogInFragment : Fragment() {

    private lateinit var binding: FragmentPhoneLogInBinding
    private lateinit var viewModel: PhoneAuthViewmodel


    private var phoneOtpVerificationFragment = PhoneOTPVerificationFragment()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_phone_log_in, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[PhoneAuthViewmodel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.onLoginClickResponse.observe(viewLifecycleOwner) {
            if (it) {
                binding.ProgressBar.visibility = View.VISIBLE
                login()
                viewModel.onLoginClickResponse.value=false
            }
        }

        viewModel.codeSentListener.observe(viewLifecycleOwner){
            if (it){
                binding.ProgressBar.visibility = View.INVISIBLE
               findNavController().navigate(R.id.to_otp)
            }
        }
    }
    private fun login() {
        val phone = "+91" +viewModel.phone.value
        sendVerificationCode(phone)
    }

    private fun sendVerificationCode(phone: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phone)       // Phone number to verify
            .setActivity(requireActivity())
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setCallbacks(viewModel.callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}