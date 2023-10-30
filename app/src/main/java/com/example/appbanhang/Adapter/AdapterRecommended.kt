package com.example.appbanhang.Adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.R
import com.squareup.picasso.Picasso

class AdapterRecommended(private val itemList : ArrayList<DataRecommended>):
    RecyclerView.Adapter<AdapterRecommended.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val img1 = itemView.findViewById<ImageView>(R.id.img1)
        val des1 = itemView.findViewById<TextView>(R.id.des1)
        val price = itemView.findViewById<TextView>(R.id.price)

        fun bindData(data: DataRecommended) {
            // Decode the Base64 image string
            val base64Image = data.img
            if (base64Image != null) {
                val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
                val decodedBitmap =
                    BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                img1.setImageBitmap(decodedBitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRecommended.ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_for_recommend_list,parent,false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: AdapterRecommended.ViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.bindData(currentItem)
        //Picasso.get().load(currentItem.img).into(holder.img1)
        holder.des1.text = currentItem.des1
        holder.price.text = currentItem.price.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}