package com.example.bankingapp

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainFragment : Fragment() {

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_main, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("state", AppCompatActivity.MODE_PRIVATE)

        val userName = sharedPreferences?.getString("username", "").toString()
        val phoneNo = sharedPreferences?.getString("phoneno", "").toString()

        val accountButton = view.findViewById<Button>(R.id.accountButton)

        val fundsText = view.findViewById<TextView>(R.id.funds)

        database = FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BankAccounts")
        database.child(phoneNo).child("funds").get().addOnSuccessListener {
            if(it.exists()){
                val valueAnimator = ValueAnimator.ofInt(0, it.value.toString().toInt())
                valueAnimator.duration = 1500
                valueAnimator.setInterpolator(AccelerateInterpolator(0.0005f))
                valueAnimator.addUpdateListener {
                    val animatedValue = it.animatedValue as Int
                    fundsText.text = "₹ ${animatedValue.toString()}"
                }
                valueAnimator.start()
            }
        }

        accountButton.setOnClickListener {
            database = FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
            database.child(phoneNo).child("Accounts").get().addOnSuccessListener {
                if(!it.exists()){
                    var navigateShowAccount = activity as FragmentNavigator
                    navigateShowAccount.navigateFrag(ShowAccountFragment(),true,null)
                }
            }
        }



        val transferButton = view.findViewById<Button>(R.id.transferButton)

        transferButton.setOnClickListener {
            var navigateTransfer = activity as FragmentNavigator
            navigateTransfer.navigateFrag(FundsTransferFragment(),true,null)
        }

        view.findViewById<TextView>(R.id.welcomeText).text = "Welcome " + userName + "!"
        val benButton : Button = view.findViewById(R.id.benButton)
        benButton.setOnClickListener {
            var navBeneficiaryFragment = activity as FragmentNavigator
            navBeneficiaryFragment.navigateFrag(BeneficiaryFragment(),true,null)

        }

        val billPayment : Button = view.findViewById(R.id.billPayment)
        billPayment.setOnClickListener {
            var navBillPaymentFragment = activity as FragmentNavigator
            navBillPaymentFragment.navigateFrag(BillPaymentFragment(),true,null)
        }
        
        val transactionButton: Button = view.findViewById(R.id.transactionButton)
        transactionButton.setOnClickListener {
            var navTransactionFragment = activity as FragmentNavigator
            navTransactionFragment.navigateFrag(TransactionFragment(),true,null)
        }


        return view
    }

}