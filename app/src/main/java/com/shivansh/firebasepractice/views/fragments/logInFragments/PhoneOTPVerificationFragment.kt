package com.shivansh.firebasepractice.views.fragments.logInFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.*
import com.shivansh.firebasepractice.R
import com.shivansh.firebasepractice.databinding.FragmentPhoneOTPVerificationBinding
import com.shivansh.firebasepractice.viewModels.PhoneAuthViewmodel
import com.shivansh.firebasepractice.views.activities.HomeActivity

class PhoneOTPVerificationFragment : Fragment() {

    private lateinit var binding : FragmentPhoneOTPVerificationBinding
    private lateinit var viewModel : PhoneAuthViewmodel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_phone_o_t_p_verification, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[PhoneAuthViewmodel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.singingListener.observe(viewLifecycleOwner){
            if (it){
                startActivity(Intent(activity, HomeActivity::class.java))
                activity?.finish()
            }else{
                Toast.makeText(context,"Invalid OTP", Toast.LENGTH_SHORT).show()
            }
        }

    }

}