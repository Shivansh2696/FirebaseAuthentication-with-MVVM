package com.shivansh.firebasepractice.dialogs

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shivansh.firebasepractice.databinding.DailogProfilePictureBinding

class ChoosePictureDialog(private var activity: Activity) : Dialog(activity) {
    private lateinit var binding : DailogProfilePictureBinding
    init {
        setCancelable(true)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DailogProfilePictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  When Clicked On Camera
        binding.ImageCamera.setOnClickListener {
            askCameraPermission()
            dismiss()
        }

        // When Clicked On Gallery
        binding.ImageGallery.setOnClickListener {
            askStoragePerssmission()
            dismiss()
        }
    }

    private fun askCameraPermission(){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,arrayOf(Manifest.permission.CAMERA), 101)
        } else {
            openCamera()
        }
    }
    private fun askStoragePerssmission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 104)
        } else {
            openGallery()
        }
    }

    private fun openGallery(){
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(pickPhoto, 103)
        dismiss()
    }

    private fun openCamera() {
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(camera, 102)
        dismiss()
    }

}