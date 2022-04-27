package com.example.bankingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_register, container, false)

        view.findViewById<EditText>(R.id.phoneno).setText(arguments?.getString("mobile"))

        var registerBtn = view.findViewById<Button>(R.id.registerButton)
        registerBtn.setOnClickListener {
            val username : String = view.findViewById<EditText>(R.id.username).text.toString()
            val password : String = view.findViewById<EditText>(R.id.password).text.toString()
            val cpassword : String = view.findViewById<EditText>(R.id.cpassword).text.toString()
            val phoneno : String = view.findViewById<EditText>(R.id.phoneno).text.toString()

            if(username.isEmpty() || password.isEmpty() || cpassword.isEmpty() || phoneno.isEmpty()){
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
            else if(password != cpassword){
                Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
            }
            else{
                database = FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                var user = User(username, password, phoneno)

                database.child(phoneno).get().addOnSuccessListener {
                    if(it.exists()){
                        Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        database.child(phoneno).child("UserInfo").setValue(user)
                        Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show()
                        var navLogin = activity as FragmentNavigator
                        navLogin.navigateFrag(LoginFragment(),false, null)
                    }
                }
            }
        }

        return view
    }

}