package com.example.bankingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpFragment : Fragment() {

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_otp, container, false)

        auth = FirebaseAuth.getInstance()

        val mobile = arguments?.getString("mobile").toString()
        val storedVerificationId= arguments?.getString("storedVerificationId").toString()


        val verifyButton : Button = view.findViewById(R.id.verifyButton)

        verifyButton.setOnClickListener {
            val otp_input: String = view.findViewById<EditText>(R.id.otp_input).text.toString()
            if (otp_input.isNotEmpty()) {
                val credential = PhoneAuthProvider.getCredential(storedVerificationId, otp_input)
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Toast.makeText(context,"Verification Successful",Toast.LENGTH_SHORT).show()
                    val bundle = Bundle()
                    bundle.putString("mobile", arguments?.getString("mobile").toString())
                    var navRegister = activity as FragmentNavigator
                    navRegister.navigateFrag(RegisterFragment(),false,bundle)
                } else {
                    Toast.makeText(context,"Verification Failed",Toast.LENGTH_SHORT).show()
                }
            }
    }

}