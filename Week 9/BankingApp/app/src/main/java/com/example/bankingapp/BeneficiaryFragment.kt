package com.example.bankingapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.coroutines.*

class BeneficiaryFragment : Fragment() {

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_beneficiary, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.layoutManager = GridLayoutManager(this.context, 1)

        val sharedPreferences = activity?.getSharedPreferences("state", Context.MODE_PRIVATE)
        val phoneno = sharedPreferences?.getString("phoneno", null).toString()
        var beneficiaryList = ArrayList<BeneficiaryDetails>()
        val adapter = BeneficiaryAdapter(beneficiaryList)

        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        database.child(phoneno).child("Beneficiaries").get().addOnSuccessListener {

            for (child in it.children) {

                val beneficiary = child.getValue(BeneficiaryDetails::class.java)
                beneficiaryList.add(beneficiary!!)
            }
            Log.d("beneficiaryList5555", beneficiaryList.toString())
            adapter.notifyDataSetChanged()
        }



        adapter.setOnClickListener(object : BeneficiaryAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                adapter.notifyDataSetChanged()
                val bundle = Bundle()
                bundle.putString("name", beneficiaryList[position].accountholdername)
                bundle.putString("accountNumber", beneficiaryList[position].accountno)
                bundle.putString("phoneno", beneficiaryList[position].phoneno)
                Log.d("beneficiaryList", beneficiaryList.toString())
                val navPayBeneficiary = activity as FragmentNavigator
                navPayBeneficiary.navigateFrag(PayBeneficiary(),true, bundle)
            }
        })




        val addBeneficiaryButton = view.findViewById<Button>(R.id.addBeneficiaryButton)
        addBeneficiaryButton.setOnClickListener {
            val addBeneficiaryFragment = activity as FragmentNavigator
            addBeneficiaryFragment.navigateFrag(AddBeneficiaryFragment(),true,null)
        }

        return view
    }

    suspend fun getBeneficiaries(phoneno : String) : ArrayList<BeneficiaryDetails>{
        Log.d("phoneno", phoneno)
        val beneficiaryList = ArrayList<BeneficiaryDetails>()
        database.child(phoneno).child("Beneficiaries").get().addOnSuccessListener {

            for (child in it.children) {

                val beneficiary = child.getValue(BeneficiaryDetails::class.java)
                beneficiaryList.add(beneficiary!!)
            }
            Log.d("beneficiaryList5555", beneficiaryList.toString())

        }
        Log.d("beneficiaryList6666", beneficiaryList.toString())

        return beneficiaryList
    }

}