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
import androidx.navigation.fragment.findNavController
import com.shivansh.firebasepractice.R
import com.shivansh.firebasepractice.databinding.FragmentLogInBinding
import com.shivansh.firebasepractice.viewModels.LoginFragmentViewModel
import com.shivansh.firebasepractice.views.activities.HomeActivity
import com.shivansh.firebasepractice.views.activities.authentication.SignUpActivity

class LogInFragment : Fragment() {

    private lateinit var binding : FragmentLogInBinding
    private lateinit var viewModel : LoginFragmentViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_log_in, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.onLoginUserResponse.observe(viewLifecycleOwner, Observer {
            if(it){
                activity?.startActivity(Intent(context, HomeActivity::class.java))
                Toast.makeText(context,"Successfully Logged In", Toast.LENGTH_SHORT).show()
                activity?.finish()
            }
        })

        viewModel.onSignUpResponse.observe(viewLifecycleOwner, Observer {
            if(it){
                activity?.startActivity(Intent(context, SignUpActivity::class.java))
                viewModel.onSignUpResponse.value = false
            }
        })

        binding.PhoneVerification.setOnClickListener{
            findNavController().navigate(R.id.action_logInFragment_to_phoneLogInFragment)
        }
    }
}