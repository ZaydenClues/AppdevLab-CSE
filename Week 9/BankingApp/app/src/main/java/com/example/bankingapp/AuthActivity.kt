package com.example.bankingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class AuthActivity : AppCompatActivity(), FragmentNavigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportActionBar?.hide()

        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragment, LoginFragment())
            .commit()
    }

    override fun navigateFrag(fragment: Fragment, addToStack: Boolean, bundle: Bundle?) {
        fragment.arguments = bundle
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.flFragment, fragment)

        if (addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}