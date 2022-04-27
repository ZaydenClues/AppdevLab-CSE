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
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import java.text.SimpleDateFormat
import java.util.*

class FundsTransferFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_funds_transfer, container, false)


        val payButton: Button = view.findViewById(R.id.payButton)
        payButton.setOnClickListener {
            val name: String = view.findViewById<EditText>(R.id.name).text.toString()
            val accountNumber: String =
                view.findViewById<EditText>(R.id.account_number).text.toString()
            val branchDetails: String =
                view.findViewById<EditText>(R.id.branchDetails).text.toString()
            var phoneno: String = view.findViewById<EditText>(R.id.phoneno).text.toString()
            val ifscCode: String = view.findViewById<EditText>(R.id.ifsc_code).text.toString()

            var cpp: CountryCodePicker = view.findViewById(R.id.ccp)
            var countryCode: String = cpp.selectedCountryCode
            phoneno = "+$countryCode$phoneno"

            if (name.isEmpty() || accountNumber.isEmpty() || branchDetails.isEmpty() || phoneno.isEmpty() || ifscCode.isEmpty()) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val amount: String = view.findViewById<EditText>(R.id.amount).text.toString()
                val storedPreferences =
                    activity?.getSharedPreferences("state", Context.MODE_PRIVATE)
                val userPhoneno = storedPreferences?.getString("phoneno", "").toString()
                database =
                    FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("BankAccounts")
                database2 =
                    FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("Users")
                database.child(phoneno).get().addOnSuccessListener {
                    if (it.child("funds").value.toString().toInt() >= amount.toInt()) {
                        val calendar = Calendar.getInstance()
                        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                        val id = System.currentTimeMillis().toString()
                        val dateTime = simpleDateFormat.format(calendar.time).toString()
                        database.child(userPhoneno).child("funds")
                            .setValue(it.child("funds").value.toString().toInt() - amount.toInt())
                        database2.child(userPhoneno).child("TransactionHistory").child(id).child("timeinitiated")
                            .setValue(dateTime)
                        database2.child(userPhoneno).child("TransactionHistory").child(id).child("transactionid")
                            .setValue(id)
                        database2.child(userPhoneno).child("TransactionHistory").child(id).child("type")
                            .setValue("DEBIT")
                        database2.child(userPhoneno).child("TransactionHistory").child(id).child("amounttransferred")
                            .setValue(amount)
                        database2.child(userPhoneno).child("TransactionHistory").child(id).child("to")
                            .setValue(accountNumber)
                        database.child(userPhoneno).child("accountno").get().addOnSuccessListener {
                            val userAccountno = it.value.toString()
                            database2.child(userPhoneno).child("TransactionHistory").child(id).child("from")
                                .setValue(userAccountno)
                            database2.child(phoneno).child("TransactionHistory").child(id).child("from")
                                .setValue(userAccountno)
                        }
                        database.child(phoneno).child("funds").get().addOnSuccessListener {
                            database.child(arguments?.getString("phoneno").toString()).child("funds")
                                .setValue(it.value.toString().toInt() + amount.toInt())
                        }
                        database2.child(phoneno).child("TransactionHistory").child(id).child("timeinitiated")
                            .setValue(dateTime)
                        database2.child(phoneno).child("TransactionHistory").child(id).child("transactionid")
                            .setValue(id)
                        database2.child(phoneno).child("TransactionHistory").child(id).child("type")
                            .setValue("CREDIT")
                        database2.child(phoneno).child("TransactionHistory").child(id).child("amounttransferred")
                            .setValue(amount)
                        database2.child(phoneno).child("TransactionHistory").child(id).child("to")
                            .setValue(accountNumber)
                        Toast.makeText(
                            context,
                            "Payment of $amount sent to $name",
                            Toast.LENGTH_SHORT
                        ).show()

                        val navigateMain = activity as FragmentNavigator
                        navigateMain.navigateFrag(MainFragment(),false,null)
                    } else {
                        Toast.makeText(activity, "Insufficient Funds", Toast.LENGTH_SHORT).show()
                    }

                }

            }


        }
        return view
    }
}