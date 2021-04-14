package com.example.shareimage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.imageView)
    }

    fun share(view: View) {
        var drawable = image.drawable as BitmapDrawable
        var bitmap = drawable.bitmap
        try {
            var file = File(application.externalCacheDir, "image.jpg")
            var fout = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout)
            fout.flush()
            fout.close()
            file.setReadable(true, false)
            var uri = FileProvider.getUriForFile(this, "com.example.shareimage.provider", file)
            var intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(intent)
        } catch (e: FileNotFoundException) {
            Log.d("TAG", "share: Something went wrong")
        }
    }
}