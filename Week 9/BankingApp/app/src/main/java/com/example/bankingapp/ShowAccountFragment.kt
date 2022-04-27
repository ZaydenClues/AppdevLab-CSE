package com.example.bankingapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ShowAccountFragment : Fragment() {

    private lateinit var database : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_show_account, container, false)

        val storedPreferences = activity?.getSharedPreferences("state", Context.MODE_PRIVATE)

        val username = storedPreferences?.getString("username", "")
        val phoneno = storedPreferences?.getString("phoneno", "").toString()

        database = FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BankAccounts")
        database.child(phoneno).get().addOnSuccessListener {
            if(it.exists()){
                view.findViewById<EditText>(R.id.username).setText(username)
                view.findViewById<EditText>(R.id.phoneno).setText(phoneno)
                view.findViewById<EditText>(R.id.name).setText(it.child("accountholdername").value.toString())
                view.findViewById<EditText>(R.id.accountno).setText(it.child("accountno").value.toString())
                view.findViewById<EditText>(R.id.branchDetails).setText(it.child("branchdetails").value.toString())
                view.findViewById<EditText>(R.id.ifsc_code).setText(it.child("ifsccode").value.toString())
            }
        }


        return view
    }
}