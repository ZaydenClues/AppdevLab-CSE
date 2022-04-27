package com.example.bankingapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class BillPaymentFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_bill_payment, container, false)

        val dropDown: AutoCompleteTextView = view.findViewById(R.id.service)
        var services = ArrayList<String>()
        services.add("Mobile Recharge")
        services.add("TV Recharge")
        services.add("Electricity Bill")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, services)

        dropDown.setAdapter(adapter)

        val payButton = view.findViewById<Button>(R.id.payButton)
        payButton.setOnClickListener {
            val amount: String = view.findViewById<EditText>(R.id.amount).text.toString()
            val storedPreferences = activity?.getSharedPreferences("state", Context.MODE_PRIVATE)
            val phoneno = storedPreferences?.getString("phoneno", "").toString()
            val service = dropDown.text.toString()
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
                    database2.child(phoneno).child("TransactionHistory").child(id)
                        .child("timeinitiated")
                        .setValue(dateTime)
                    database2.child(phoneno).child("TransactionHistory").child(id)
                        .child("transactionid")
                        .setValue(id)
                    database2.child(phoneno).child("TransactionHistory").child(id).child("type")
                        .setValue("DEBIT")
                    database2.child(phoneno).child("TransactionHistory").child(id)
                        .child("amounttransferred")
                        .setValue(amount)
                    database2.child(phoneno).child("TransactionHistory").child(id).child("to")
                        .setValue(service)
                    database.child(phoneno).child("accountno").get().addOnSuccessListener {
                        val userAccountno = it.value.toString()
                        database2.child(phoneno).child("TransactionHistory").child(id).child("from")
                            .setValue(userAccountno)
                    }

                    database.child(arguments?.getString("phoneno").toString()).child("funds").get()
                        .addOnSuccessListener {
                            database.child(arguments?.getString("phoneno").toString())
                                .child("funds")
                                .setValue(it.value.toString().toInt() + amount.toInt())
                        }

                    Toast.makeText(
                        context,
                        "Payment of $amount sent to $service",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(activity, "Insufficient Funds", Toast.LENGTH_SHORT).show()
                }
            }


        }
        return view
    }

    private fun getServiceList(): ArrayList<String>? {
        val services = ArrayList<String>()
        services.add("Mobile Recharge")
        services.add("TV Recharge")
        services.add("Electricity Bill")
        return services
    }
}
