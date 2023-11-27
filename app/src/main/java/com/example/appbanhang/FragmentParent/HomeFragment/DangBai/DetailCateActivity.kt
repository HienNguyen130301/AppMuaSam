package com.example.appbanhang.FragmentParent.HomeFragment.DangBai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhang.Adapter.AdapterRecommended
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.R
import com.example.appbanhang.databinding.ActivityDetailCate2Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailCateActivity : BaseActivity<ActivityDetailCate2Binding>() {

    private lateinit var mAdapter: AdapterRecommended
    private lateinit var ds1: ArrayList<DataRecommended>

    private lateinit var dbRef: DatabaseReference

    override val layoutId: Int
        get() = R.layout.activity_detail_cate2

    override fun setupUI() {
        super.setupUI()

        val bundle: Bundle? = intent.extras
        val key = bundle?.getString("des")

        binding.apply {
            rcvDetail.setHasFixedSize(true)
            rcvDetail.layoutManager = GridLayoutManager(this@DetailCateActivity, 2)
            ds1 = arrayListOf<DataRecommended>()
            mAdapter = AdapterRecommended(ds1)
            rcvDetail.adapter = mAdapter
        }

        if (key == "Sneaker") {
            dbRef = FirebaseDatabase.getInstance().getReference("Sneaker")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ds1.clear()
                    if (snapshot.exists()) {
                        for (empSnap in snapshot.children) {
                            val empData = empSnap.getValue(DataRecommended::class.java)
                            ds1.add(empData!!)
                        }
                        mAdapter.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        if (key == "Shoes") {
            dbRef = FirebaseDatabase.getInstance().getReference("Shoes")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ds1.clear()
                    if (snapshot.exists()) {
                        for (empSnap in snapshot.children) {
                            val empData = empSnap.getValue(DataRecommended::class.java)
                            ds1.add(empData!!)
                        }
                        mAdapter.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        if(key == "Apparel") {
            dbRef = FirebaseDatabase.getInstance().getReference("Apparel")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ds1.clear()
                    if (snapshot.exists()) {
                        for (empSnap in snapshot.children){
                            val empData = empSnap.getValue(DataRecommended::class.java)
                            ds1.add(empData!!)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}