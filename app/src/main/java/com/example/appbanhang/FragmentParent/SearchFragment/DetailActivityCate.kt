package com.example.appbanhang.FragmentParent.SearchFragment

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbanhang.Adapter.AdapterRecommended
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.R
import com.example.appbanhang.databinding.ActivityDetailCateBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

class DetailActivityCate : BaseActivity<ActivityDetailCateBinding>() {

    private lateinit var ds1: ArrayList<DataRecommended>

    override val layoutId: Int
        get() = R.layout.activity_detail_cate

    override fun setupUI() {
        super.setupUI()

        ds1 = arrayListOf<DataRecommended>()

        val bundle : Bundle?= intent.extras
        val tenSP = bundle?.getString("tenSP")
        val imameDetail = bundle?.getString("ImageURL")

        //binding.tenSPDetail.text = tenSP

        /*if (imameDetail != null) {
            val decodedBytes = Base64.decode(imameDetail, Base64.DEFAULT)
            val decodedBitmap =
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            binding.imgDetail.setImageBitmap(decodedBitmap)
        }
        binding.btnUpdateData.setOnClickListener {
            val newTenSP = binding.edtTenSp.text.toString()
            updateDataToFirebase(newTenSP)
        }*/
    }

    private fun updateDataToFirebase(newTenSP: String) {
        val bundle : Bundle?= intent.extras
        val key = bundle?.getString("key")
        val dbRef = FirebaseDatabase.getInstance().getReference("ThemBaiDang").child(key!!)
        Log.d("------", "updateDataToFirebase: $dbRef ")
        dbRef.child("tenSP").setValue(newTenSP)
    }
}
