package com.example.appbanhang.FragmentParent.HomeFragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.FragmentParent.MessageFragment.MessageFragment
import com.example.appbanhang.R
import com.example.appbanhang.databinding.ActivityDetailRecommendBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailActivityRecommend : BaseActivity<ActivityDetailRecommendBinding>() {

    private lateinit var ds1: ArrayList<DataRecommended>
    private lateinit var dbRef: DatabaseReference

    override val layoutId: Int
        get() = R.layout.activity_detail_recommend

    override fun setupUI() {
        super.setupUI()

        ds1 = arrayListOf<DataRecommended>()

        val bundle: Bundle? = intent.extras
        val key = bundle?.getString("key")

        dbRef =FirebaseDatabase.getInstance().getReference("ThemBaiDang").child(key!!)
        dbRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val dataRecommended = snapshot.getValue(DataRecommended::class.java)
                    if (dataRecommended != null){
                        binding.txtTenSpRcm.text = dataRecommended.tenSP
                        binding.txtGiaRcm.text = dataRecommended.price
                        binding.txtDesRcm.text = dataRecommended.des
                        binding.txtTypeRcm.text = dataRecommended.type

                        if (dataRecommended.imageUrl != null) {
                            val decodedBytes = Base64.decode(dataRecommended.imageUrl, Base64.DEFAULT)
                            val decodedBitmap =
                                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                            binding.imageDetailRcm.setImageBitmap(decodedBitmap)
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun setupListener() {
        super.setupListener()

        binding.apply {
            themGioHang.setOnClickListener {

            }
            messageUser.setOnClickListener {

            }
        }
    }
}