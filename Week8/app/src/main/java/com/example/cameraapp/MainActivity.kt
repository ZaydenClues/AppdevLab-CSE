package com.example.cameraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val cameraBtn: Button = findViewById(R.id.camera)
        val galleryBtn: Button = findViewById(R.id.gallery)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cameraBtn.setOnClickListener {
            Toast.makeText(this, "lol", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        galleryBtn.setOnClickListener {
            Toast.makeText(this, "lol2", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }
    }

}