package com.example.bankingapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BeneficiaryAdapter(private val mList: MutableList<BeneficiaryDetails>):
        RecyclerView.Adapter<BeneficiaryAdapter.ViewHolder>() {
    private lateinit var database: DatabaseReference
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.beneficiary_view_design, parent, false)



        return ViewHolder(view, mListener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val BeneficiaryDetails = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.nickname.setText(BeneficiaryDetails.nickname)

        // sets the text to the textview from our itemHolder class
        holder.name.text = "Name : ${BeneficiaryDetails.accountholdername}"

        holder.phoneno.text = "Phone no. : ${BeneficiaryDetails.phoneno}"

        holder.editButton.setOnClickListener {
            holder.editNickname.isEnabled = true
            holder.editNickname.visibility = View.VISIBLE
            holder.editNickname.setText(BeneficiaryDetails.nickname)
            holder.nickname.visibility = View.GONE
            holder.editButton.visibility = View.GONE
            holder.saveButton.visibility = View.VISIBLE

        }

        holder.saveButton.setOnClickListener {
            val nickname = holder.editNickname.text.toString()
            database =
                FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Users")
            val sharedPreferences = holder.itemView.context.getSharedPreferences("state", Context.MODE_PRIVATE)
            val userPhoneno = sharedPreferences.getString("phoneno", "").toString()
            val phoneno = BeneficiaryDetails.phoneno.toString()
            database.child(userPhoneno).child("Beneficiaries").get().addOnSuccessListener {
                if(it.child(phoneno).exists()){
                    database.child(userPhoneno).child("Beneficiaries").child(phoneno).child("nickname").setValue(holder.editNickname.text.toString())
                }
            }
            BeneficiaryDetails.nickname = nickname
            holder.nickname.text = nickname
            holder.nickname.visibility = View.VISIBLE
            holder.editNickname.isEnabled = false
            holder.editNickname.visibility = View.GONE
            holder.saveButton.visibility = View.GONE
            holder.editButton.visibility = View.VISIBLE
        }

        holder.deleteButton.setOnClickListener {
            database =
                FirebaseDatabase.getInstance("https://bankapp-aa2ba-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Users")
            val sharedPreferences = holder.itemView.context.getSharedPreferences("state", Context.MODE_PRIVATE)
            val userPhoneno = sharedPreferences.getString("phoneno", "").toString()
            val phoneno = BeneficiaryDetails.phoneno.toString()
            database.child(userPhoneno).child("Beneficiaries").get().addOnSuccessListener {
                if(it.child(phoneno).exists()){
                    database.child(userPhoneno).child("Beneficiaries").child(phoneno).removeValue()
                }
            }
            mList.removeAt(position)
            notifyDataSetChanged()
        }

    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(ItemView) {
        val editNickname: EditText = itemView.findViewById(R.id.editNickname)
        val nickname: TextView = itemView.findViewById(R.id.nickname)
        val name: TextView = itemView.findViewById(R.id.name)
        val phoneno: TextView = itemView.findViewById(R.id.phoneno)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val saveButton: Button = itemView.findViewById(R.id.saveButton)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}