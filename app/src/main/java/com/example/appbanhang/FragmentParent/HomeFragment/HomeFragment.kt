package com.example.appbanhang.FragmentParent.HomeFragment

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
import com.google.firebase.auth.FirebaseAuth
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
        binding.searchView1.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchFirebaseData(newText)
                }
                return true
            }
        })
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
                        Log.d("------", "onDataChange: $key")
                    }
                    mAdapter = AdapterRecommended(ds1)
                    binding.rcv3.adapter = mAdapter

                    mAdapter.setonItemClickListener(object :
                        AdapterRecommended.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireContext(), DetailActivityRecommend::class.java)
                            intent.putExtra("key", ds1[position].key)
                            Log.d("------", "onDataChange: ${ds1[position].key}")
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
                binding.giam.visibility = View.VISIBLE
                binding.tang.visibility = View.GONE
            } else {
                binding.giam.visibility = View.GONE
                binding.tang.visibility = View.VISIBLE
            }
        }
        binding.sortAvail.setOnClickListener {
            sortList = !sortList
            loadDataSortedByAvailable(sortList)
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
    private fun loadDataSortedByAvailable(availableOnly: Boolean) {
        dbRef = FirebaseDatabase.getInstance().getReference("ThemBaiDang")

        dbRef.orderByChild("available").equalTo(true).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ds1.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val empData = empSnap.getValue(DataRecommended::class.java)
                        ds1.add(empData!!)
                        val key = empSnap.key
                        empData.key = key
                    }

                    // Sort or reverse based on your requirement
                    ds1.sortBy { it.price }
                    if (!sortList) {
                        ds1.reverse()
                    }

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

                    binding.txtTangGiam.tag = sortList
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
    private fun searchFirebaseData(query: String) {
        val searchQuery = query.toLowerCase()
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser?.uid
        dbRef = FirebaseDatabase.getInstance().getReference("ThemBaiDang")
        val firebaseSearchQuery = dbRef.orderByChild("tenSP").startAt(searchQuery).endAt(searchQuery + "\uf8ff")

        firebaseSearchQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val searchResults = arrayListOf<DataRecommended>()
                for (snapshot in dataSnapshot.children) {
                    val yourDataModel = snapshot.getValue(DataRecommended::class.java)
                    yourDataModel?.let {
                        searchResults.add(it)
                    }
                }
                mAdapter.submitList(searchResults)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}