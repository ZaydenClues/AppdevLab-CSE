package com.example.bankingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import java.util.concurrent.TimeUnit


class MobileFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_mobile, container, false)

        auth = FirebaseAuth.getInstance()

        val sendOtpButton = view.findViewById<Button>(R.id.otpButton)

        sendOtpButton.setOnClickListener {
            var number : String = view.findViewById<EditText>(R.id.mobile_number).text.toString()
            if(number.isNotEmpty()){
                var cpp : CountryCodePicker = view.findViewById(R.id.ccp)
                var countryCode : String = cpp.selectedCountryCode
                var numberCC = "+$countryCode$number"
                database = FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BankAccounts")
                database.child(numberCC).get().addOnSuccessListener {
                    if(it.exists()){
                        database =FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                        database.child(numberCC).get().addOnSuccessListener {
                            if(it.exists()){
                                Toast.makeText(context, "Account already exists", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                sendVerificationCode(numberCC)
                            }

                        }

                    }else{
                        Toast.makeText(context, "Your mobile no. is not registered to any account", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Enter a valid mobile number", Toast.LENGTH_SHORT).show()
            }

        }

        val loginHref : Button = view.findViewById(R.id.loginHref)

        loginHref.setOnClickListener {
            var navLogin = activity as FragmentNavigator
            navLogin.navigateFrag(LoginFragment(), true, null)
        }

        callbacks = object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                storedVerificationId = verificationId
                resendToken = token

                val bundle = Bundle()
                var number : String = view.findViewById<EditText>(R.id.mobile_number).text.toString()
                number = "+91$number"
                bundle.putString("mobile", number)
                bundle.putString("storedVerificationId", storedVerificationId)


                var navOtp = activity as FragmentNavigator
                navOtp.navigateFrag(OtpFragment(), true, bundle)
                Toast.makeText(context, "OTP sent", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60, TimeUnit.SECONDS)
            .setActivity(AuthActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}