package com.example.appbanhang.FragmentParent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbanhang.Adapter.AdapterCate
import com.example.appbanhang.Adapter.AdapterRecommended
import com.example.appbanhang.DangBaiActivity
import com.example.appbanhang.Data.DataCate
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView1: RecyclerView
    private lateinit var ds: ArrayList<DataCate>
    private lateinit var ds1: ArrayList<DataRecommended>
    private lateinit var adapterCate: AdapterCate
    private lateinit var adapterRecommended: AdapterRecommended

    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.rcv1)
        recyclerView1 = view.findViewById(R.id.rcv2)
        HRcv()
        GRcv()
        gotoDangBai()

        recyclerView1.setHasFixedSize(true)
        recyclerView1.layoutManager = GridLayoutManager(requireContext(),2)
        ds1 = arrayListOf<DataRecommended>()
    }

    private fun gotoDangBai(){

        val mbtnTest = view?.findViewById<ImageView>(R.id.btntest)
        val intent = Intent(requireContext(),DangBaiActivity::class.java)
        mbtnTest?.setOnClickListener {
            startActivity(intent)
        }

    }

    private fun HRcv(){

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        ds = ArrayList()

        hehe()

        adapterCate = AdapterCate(ds)
        recyclerView.adapter = adapterCate
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
                    }
                    val mAdapter = AdapterRecommended(ds1)
                    recyclerView1.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun hehe(){
        ds.add(DataCate(R.drawable.anhconthodan, "Hehehehe"))
        ds.add(DataCate(R.drawable.anhconthodan, "Hehehehe"))
        ds.add(DataCate(R.drawable.anhconthodan, "Hehehehe"))
        ds.add(DataCate(R.drawable.anhconthodan, "Hehehehe"))

    }
}