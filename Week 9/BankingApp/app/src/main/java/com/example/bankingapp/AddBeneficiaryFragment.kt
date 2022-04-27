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

class AddBeneficiaryFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_beneficiary, container, false)

        val addButton : Button = view.findViewById(R.id.addButton)
        addButton.setOnClickListener{
            val nickname : String = view.findViewById<EditText>(R.id.nickname).text.toString()
            val name : String = view.findViewById<EditText>(R.id.name).text.toString()
            val accountNumber : String = view.findViewById<EditText>(R.id.account_number).text.toString()
            val branchDetails : String = view.findViewById<EditText>(R.id.branchDetails).text.toString()
            var phoneno : String = view.findViewById<EditText>(R.id.phoneno).text.toString()
            val ifscCode : String = view.findViewById<EditText>(R.id.ifsc_code).text.toString()

            var cpp : CountryCodePicker = view.findViewById(R.id.ccp)
            var countryCode : String = cpp.selectedCountryCode
            phoneno = "+$countryCode$phoneno"

            if(nickname.isEmpty() || name.isEmpty() || accountNumber.isEmpty() || branchDetails.isEmpty() || phoneno.isEmpty() || ifscCode.isEmpty()){
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
            else{
                database = FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                val beneficiaryDetails = BeneficiaryDetails(nickname, name, accountNumber, branchDetails, phoneno, ifscCode)

                val sharedPreferences = activity?.getSharedPreferences("state", Context.MODE_PRIVATE)
                val userPhone = sharedPreferences?.getString("phoneno", null).toString()

                database.child(userPhone).child("Beneficiaries").get().addOnSuccessListener {
                    if(it.child(phoneno).exists()){
                        Toast.makeText(context, "Beneficiary already exists", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        database2 = FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BankAccounts")
                        database2.child(phoneno).get().addOnSuccessListener {
                            if(it.exists()){
                                if(it.child("accountno").value.toString() == accountNumber &&
                                    it.child("ifsccode").value.toString() == ifscCode &&
                                    it.child("branchdetails").value.toString() == branchDetails &&
                                    it.child("phoneno").value.toString() == phoneno){
                                    database.child(userPhone).child("Beneficiaries").child(phoneno).setValue(beneficiaryDetails)
                                    Toast.makeText(context, "Beneficiary added", Toast.LENGTH_SHORT).show()
                                    var navigateBeneficiary = activity as FragmentNavigator
                                    navigateBeneficiary.navigateFrag(BeneficiaryFragment(),false,null)
                                }
                                else{
                                    Toast.makeText(context, "Invalid Details", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                    Toast.makeText(context, "No account found with provided phone number", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }

            }
        }
        return view
    }

}