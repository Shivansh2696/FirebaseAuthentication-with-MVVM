package com.shivansh.firebasepractice.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import java.io.ByteArrayOutputStream

object ImageConverter {

     fun convertImageToString(imageView: ImageView): String {
         imageView.buildDrawingCache()
         val bitmap = imageView.drawingCache
         val byteArrayOutputStream = ByteArrayOutputStream()
         bitmap?.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
         val b = byteArrayOutputStream.toByteArray()
         return Base64.encodeToString(b, Base64.DEFAULT)
     }

    fun convertStringTOBitmap(stringImage: String?): Bitmap? {
        val data: ByteArray = Base64.decode(stringImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }
}