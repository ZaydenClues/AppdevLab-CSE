package com.example.bankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), FragmentNavigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val logoutButton : Button = findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("state", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear();
            editor.putBoolean("loggedIn", false)
            editor.putString("username", "")
            editor.putString("phoneno", "")
            editor.commit()

            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainflFragment, MainFragment())
            .commit()




    }

    override fun navigateFrag(fragment: Fragment, addToStack: Boolean, bundle: Bundle?) {
        fragment.arguments = bundle
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainflFragment, fragment)

        if (addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}