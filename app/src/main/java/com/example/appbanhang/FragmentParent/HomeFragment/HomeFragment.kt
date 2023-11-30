package com.example.appbanhang.FragmentParent.HomeFragment

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.appbanhang.Adapter.AdapterCate
import com.example.appbanhang.Adapter.AdapterRecommended
import com.example.appbanhang.Adapter.ImageAdapterViewPager2
import com.example.appbanhang.Base.BaseFragment
import com.example.appbanhang.Data.DataCate
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.FragmentParent.HomeFragment.DangBai.DangBaiActivity
import com.example.appbanhang.FragmentParent.HomeFragment.DangBai.DetailCateActivity
import com.example.appbanhang.R
import com.example.appbanhang.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var ds: ArrayList<DataCate>
    private lateinit var ds1: ArrayList<DataRecommended>
    private lateinit var adapterCate: AdapterCate
    private lateinit var mAdapter: AdapterRecommended

    private lateinit var viewPager: ViewPager2
    private lateinit var imageList: List<Int>

    private lateinit var dbRef: DatabaseReference

    private var sortList = false

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun setupUI() {
        super.setupUI()
        HRcv1()
        GRcv()
        displayViewPager2()

        binding.apply {
            rcv3.setHasFixedSize(true)
            rcv3.layoutManager = GridLayoutManager(requireContext(), 2)
            ds1 = arrayListOf<DataRecommended>()

        }
    }

    override fun setupListener() {
        super.setupListener()
        binding.btnFloat.setOnClickListener {
            val intent = Intent(requireContext(), DangBaiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun HRcv1() {
        binding.apply {
            rcv1.setHasFixedSize(true)
            rcv1.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            ds = ArrayList()
            SampleData()
            adapterCate = AdapterCate(ds)
            rcv1.adapter = adapterCate
        }
    }

    private fun GRcv() {

        dbRef = FirebaseDatabase.getInstance().getReference("ThemBaiDang")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ds1.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val empData = empSnap.getValue(DataRecommended::class.java)
                        ds1.add(empData!!)
                        val key = empSnap.key
                        empData.key = key
                    }
                    mAdapter = AdapterRecommended(ds1)
                    binding.rcv3.adapter = mAdapter

                    mAdapter.setonItemClickListener(object :
                        AdapterRecommended.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireContext(), DetailActivityRecommend::class.java)
                            intent.putExtra("key", ds1[position].key)
                            startActivity(intent)
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        binding.txtTangGiam.setOnClickListener {
            sortList = !sortList
            loadDataSortedByPrice(sortList)
            if (sortList == true) {
                binding.tang.visibility
                binding.giam.isInvisible
            } else {
                binding.tang.isInvisible
                binding.giam.visibility
            }
        }
    }

    private fun displayViewPager2() {
        imageList = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4,
            R.drawable.banner5
        )
        val adapter = ImageAdapterViewPager2(imageList)
        binding.viewPager2Home.adapter = adapter
    }

    private fun SampleData() {
        ds.add(DataCate(R.drawable.giay, "Sneaker"))
        ds.add(DataCate(R.drawable.dep, "Shoes"))
        ds.add(DataCate(R.drawable.ao, "Apparel"))
        ds.add(DataCate(R.drawable.ps5, "Electronics"))
        ds.add(DataCate(R.drawable.backpack, "Accessories"))
    }

    private fun loadDataSortedByPrice(ascendingOrder: Boolean) {
        dbRef = FirebaseDatabase.getInstance().getReference("ThemBaiDang")
        dbRef.orderByChild("price").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ds1.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val empData = empSnap.getValue(DataRecommended::class.java)
                        ds1.add(empData!!)
                        val key = empSnap.key
                        empData.key = key
                    }

                    ds1.sortBy { it.price }
                    if (!ascendingOrder) {ds1.reverse()}
                    mAdapter = AdapterRecommended(ds1)
                    binding.rcv3.adapter = mAdapter

                    mAdapter.setonItemClickListener(object :
                        AdapterRecommended.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent =
                                Intent(requireContext(), DetailActivityRecommend::class.java)
                            intent.putExtra("key", ds1[position].key)
                            startActivity(intent)
                        }
                    })
                    binding.txtTangGiam.tag = ascendingOrder
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}