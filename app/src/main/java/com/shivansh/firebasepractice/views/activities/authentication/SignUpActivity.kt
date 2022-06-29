package com.shivansh.firebasepractice.views.activities.authentication

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shivansh.firebasepractice.R
import com.shivansh.firebasepractice.databinding.ActivitySignUpBinding
import com.shivansh.firebasepractice.dialogs.ChoosePictureDialog
import com.shivansh.firebasepractice.models.User
import com.shivansh.firebasepractice.utils.ImageConverter
import com.shivansh.firebasepractice.viewModels.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding
    private lateinit var viewModel : SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.signUpClickResponse.observe(this, Observer {
            if(it){
                val imageInString = ImageConverter.convertImageToString(binding.ProfilePhoto)

                val user = User(binding.etEmail.text.toString().trim(),
                                binding.etName.text.toString().trim(),
                                binding.etPassword.text.toString().trim(),
                                binding.etMobile.text.toString().trim(),
                                imageInString)

                viewModel.insertUser(user).observe(this, Observer {
                    Toast.makeText(this,"Registration Done Successfully",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                })

            }
        })

        viewModel.onClickImageResponse.observe(this, Observer {
            if(it){
                ChoosePictureDialog(this).show()
            }
        })

    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String?>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else
                Toast.makeText(this,"Camera Permission is Required To Use Camera", Toast.LENGTH_SHORT).show()
        }
        if (requestCode == 104){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery()
            }else
                Toast.makeText(this,"Storage Permission is required to use Gallery", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            103 -> if (resultCode == RESULT_OK) {
                val selectedImage = data?.data
                binding.ProfilePhoto.setImageURI(selectedImage)
            }
            102 -> if (resultCode == RESULT_OK) {
                val bundle = data?.extras
                val bitmapImage = bundle!!["data"] as Bitmap
                binding.ProfilePhoto.setImageBitmap(bitmapImage)
            }
        }
    }

    private fun openCamera() {
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, 102)
    }

    private fun openGallery(){
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, 103)
    }
}