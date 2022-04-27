package com.example.bankingapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rilixtech.widget.countrycodepicker.CountryCodePicker

class LoginFragment : Fragment() {

    lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_login, container, false)
        var registerHref = view.findViewById<Button>(R.id.registerHref)



        val loginButton : Button = view.findViewById(R.id.loginButton)

        loginButton.setOnClickListener{
            val phoneno : String = view.findViewById<EditText>(R.id.mobile_number).text.toString()
            var cpp : CountryCodePicker = view.findViewById(R.id.ccp)
            var countryCode : String = cpp.selectedCountryCode
            var numberCC = "+$countryCode$phoneno"

            val password : String = view.findViewById<EditText>(R.id.password).text.toString()
            if(phoneno.isEmpty() || password.isEmpty()){
                Toast.makeText(context,"Please enter all the details",Toast.LENGTH_SHORT).show()
            }
            database = FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")

            database.child(numberCC).get().addOnSuccessListener {
                if(it.child("UserInfo").child("password").value.toString() == password){
                    val sharedPref = this.activity?.getSharedPreferences("state",Context.MODE_PRIVATE)
                    var editor = sharedPref?.edit()
                    editor?.clear();
                    editor?.putBoolean("isLoggedIn",true)
                    editor?.putString("phoneno",numberCC)
                    editor?.putString("username",it.child("UserInfo").child("username").value.toString())
                    editor?.commit()

                    activity?.let{
                        val intent = Intent(it,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }

                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Invalid user or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerHref.setOnClickListener{
            var navMobile = activity as FragmentNavigator
            navMobile.navigateFrag(MobileFragment(),false, null)
        }
        return view

    }


}