package com.example.bankingapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TransactionFragment : Fragment() {
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_transaction, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this.context, 1)

        val sharedPreferences = activity?.getSharedPreferences("state", Context.MODE_PRIVATE)
        val phoneno = sharedPreferences?.getString("phoneno", null).toString()

        var itemList = ArrayList<ItemList>()
        val adapter = ItemAdapters(itemList)

        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        database.child(phoneno).child("TransactionHistory").get().addOnSuccessListener {
            for(child in it.children){
                val item = child.getValue(ItemList::class.java)
                itemList.add(item!!)
            }

            adapter.notifyDataSetChanged()
        }

        adapter.setOnClickListener(object : ItemAdapters.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.d("itemList", itemList.toString())
            }
        })

        return view
    }

}