package com.example.bankingapp

import android.content.Context
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
import java.text.SimpleDateFormat
import java.util.Calendar

class PayBeneficiary : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_pay_beneficiary, container, false)
        view.findViewById<EditText>(R.id.name).setText(arguments?.getString("name"))
        view.findViewById<EditText>(R.id.accountNumber)
            .setText(arguments?.getString("accountNumber"))
        view.findViewById<EditText>(R.id.phoneno).setText(arguments?.getString("phoneno"))

        val payButton = view.findViewById<Button>(R.id.payButton)
        payButton.setOnClickListener {
            val amount: String = view.findViewById<EditText>(R.id.amount).text.toString()
            val storedPreferences = activity?.getSharedPreferences("state", Context.MODE_PRIVATE)
            val phoneno = storedPreferences?.getString("phoneno", "").toString()
            database =
                FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("BankAccounts")
            database2 =
                FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Users")
            database.child(phoneno).get().addOnSuccessListener {
                if (it.child("funds").value.toString().toInt() >= amount.toInt()) {
                    database.child(phoneno).child("funds")
                        .setValue(it.child("funds").value.toString().toInt() - amount.toInt())
                    val calendar = Calendar.getInstance()
                    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    val id = System.currentTimeMillis().toString()
                    val dateTime = simpleDateFormat.format(calendar.time).toString()
                    database2.child(phoneno).child("TransactionHistory").child(id).child("timeinitiated")
                        .setValue(dateTime)
                    database2.child(phoneno).child("TransactionHistory").child(id).child("transactionid")
                        .setValue(id)
                    database2.child(phoneno).child("TransactionHistory").child(id).child("type")
                        .setValue("DEBIT")
                    database2.child(phoneno).child("TransactionHistory").child(id).child("amounttransferred")
                        .setValue(amount)
                    database2.child(phoneno).child("TransactionHistory").child(id).child("to")
                        .setValue(arguments?.getString("accountNumber"))
                    database.child(phoneno).child("accountno").get().addOnSuccessListener {
                        val userAccountno = it.value.toString()
                        database2.child(phoneno).child("TransactionHistory").child(id).child("from")
                            .setValue(userAccountno)
                        database2.child(arguments?.getString("phoneno").toString()).child("TransactionHistory").child(id).child("from")
                            .setValue(userAccountno)
                    }

                    database.child(arguments?.getString("phoneno").toString()).child("funds").get().addOnSuccessListener {
                        database.child(arguments?.getString("phoneno").toString()).child("funds")
                            .setValue(it.value.toString().toInt() + amount.toInt())
                    }
                    database2.child(arguments?.getString("phoneno").toString()).child("TransactionHistory").child(id).child("timeinitiated")
                        .setValue(dateTime)
                    database2.child(arguments?.getString("phoneno").toString()).child("TransactionHistory").child(id).child("transactionid")
                        .setValue(id)
                    database2.child(arguments?.getString("phoneno").toString()).child("TransactionHistory").child(id).child("type")
                        .setValue("CREDIT")
                    database2.child(arguments?.getString("phoneno").toString()).child("TransactionHistory").child(id).child("amounttransferred")
                        .setValue(amount)

                    database2.child(arguments?.getString("phoneno").toString()).child("TransactionHistory").child(id).child("to")
                        .setValue(arguments?.getString("accountNumber"))

                    Toast.makeText(context, "Payment of $amount sent to beneficiary", Toast.LENGTH_SHORT).show()
                    val navigateBeneficiary = activity as FragmentNavigator
                    navigateBeneficiary.navigateFrag(BeneficiaryFragment(),false,null)
                } else {
                    Toast.makeText(activity, "Insufficient Funds", Toast.LENGTH_SHORT).show()
                }

            }
        }

        return view
    }

}