package com.example.bankingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapters(private val mlist: MutableList<ItemList>) : RecyclerView.Adapter<ItemAdapters.ViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_item_layout, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mlist[position]

        holder.transactionID.text = "Transaction ID: ${item.transactionid}"
        holder.from.text = "From: ${item.from}"
        holder.to.text = "To: ${item.to}"
        holder.amount.text = "Amount Transferred: ₹ ${item.amounttransferred}"
        holder.time.text = "Time: ${item.timeinitiated}"
        holder.type.text = "Type: ${item.type}"
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    class ViewHolder(ItemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
            val transactionID: TextView = itemView.findViewById(R.id.transaction_id)
            val amount: TextView = itemView.findViewById(R.id.amount)
            val from: TextView = itemView.findViewById(R.id.from)
            val to : TextView = itemView.findViewById(R.id.to)
            val time: TextView = itemView.findViewById(R.id.time)
            val type: TextView = itemView.findViewById(R.id.type)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
