package com.example.restaurantapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class  MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val name: EditText= findViewById(R.id.username)
            val password: EditText = findViewById(R.id.password)
            if(name.text.toString() != "Arakan"){0
                Toast.makeText(this, "Invalid User", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.text.toString() != "123456"){
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }
    }
}