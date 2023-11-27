package com.example.appbanhang.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appbanhang.Data.DataCate
import com.example.appbanhang.R

class AdapterCate(private val item: ArrayList<DataCate>) :
    RecyclerView.Adapter<AdapterCate.Myviewholder>() {

    private var mListener1: onItemClickListener? = null

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener(listener: onItemClickListener) {
        mListener1 = listener
    }

    inner class Myviewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val img: ImageView = itemview.findViewById(R.id.img)
        val txtDes: TextView = itemview.findViewById(R.id.txtDes)

        init {
            itemView.setOnClickListener {
                mListener1?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.item_for_cate_list, parent, false)
        return Myviewholder(itemview)
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        val currentItem = item[position]
        holder.img.setImageResource(currentItem.img)
        holder.txtDes.text = currentItem.des
    }

    override fun getItemCount(): Int {
        return item.size
    }
}