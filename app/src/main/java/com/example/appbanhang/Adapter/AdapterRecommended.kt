package com.example.appbanhang.Adapter

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.R
import org.w3c.dom.Text
import java.util.Locale

class AdapterRecommended(private var itemList: ArrayList<DataRecommended>):
    RecyclerView.Adapter<AdapterRecommended.ViewHolder>() {

    private lateinit var data: ArrayList<DataRecommended>
   private lateinit var mListener: onItemClickListener

   interface onItemClickListener{
       fun onItemClick(position: Int)
   }

    fun setonItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    inner class ViewHolder(itemView: View,listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val img1 = itemView.findViewById<ImageView>(R.id.img1)
        val tenSP = itemView.findViewById<TextView>(R.id.tenSP)
        val price = itemView.findViewById<TextView>(R.id.price)
        val type = itemView.findViewById<TextView>(R.id.type)
        val favoriteIcon = itemView.findViewById<ImageView>(R.id.checkbox)
        val isAvailible = itemView.findViewById<TextView>(R.id.availible)
        val isAvailible1 = itemView.findViewById<TextView>(R.id.notAvailible)


        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bindData(data: DataRecommended) {

            val base64Image = data.imageUrl
            if (base64Image != null) {
                val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
                val decodedBitmap =
                    BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                img1.setImageBitmap(decodedBitmap)
            }
            favoriteIcon.setImageResource(if (data.isFavorite) R.drawable.ic_check_box_24 else R.drawable.ic_check_box_outline_blank_24)
            favoriteIcon.setOnClickListener {
                data.isFavorite = !data.isFavorite
                favoriteIcon.setImageResource(
                    if (data.isFavorite) R.drawable.ic_check_box_outline_blank_24
                    else R.drawable.ic_check_box_24
                )
            }
            if (data.isAvailable == true ) {
                isAvailible.visibility = View.VISIBLE
                isAvailible1.visibility = View.GONE
            }else {
                isAvailible.visibility = View.GONE
                isAvailible1.visibility = View.VISIBLE
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRecommended.ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_for_recommend_list,parent,false)
        return ViewHolder(itemview,mListener)
    }

    override fun onBindViewHolder(holder: AdapterRecommended.ViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.bindData(currentItem)
        holder.tenSP.text = currentItem.tenSP
        holder.price.text = currentItem.price +"$"
        holder.type.text = "Type:" + currentItem.type



    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setFilterList(itemList1: ArrayList<DataRecommended>) {
        this.itemList = itemList1
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: ArrayList<DataRecommended>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }
}