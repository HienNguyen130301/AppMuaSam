package com.example.appbanhang.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.Data.DataUsers
import com.example.appbanhang.R

class AdapterListChat(private var itemList: ArrayList<DataUsers>): RecyclerView.Adapter<AdapterListChat.viewHolder>() {

    private lateinit var mListener: AdapterListChat.onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener(listener1: onItemClickListener) {
        mListener = listener1
    }

    inner class viewHolder(itemView: View,listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val txtUser: TextView = itemView.findViewById(R.id.userName)
        val layoutUser: LinearLayout = itemView.findViewById(R.id.layoutUser)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListChat.viewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return viewHolder(itemview,mListener)
    }

    override fun onBindViewHolder(holder: AdapterListChat.viewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.txtUser.text = currentItem.email

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}