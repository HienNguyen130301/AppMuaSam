package com.example.appbanhang.FragmentParent.SearchFragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbanhang.Adapter.AdapterCate
import com.example.appbanhang.Adapter.AdapterListChat
import com.example.appbanhang.Adapter.AdapterRecommended
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.Data.DataCate
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.Data.DataUsers
import com.example.appbanhang.FragmentParent.HomeFragment.DetailActivityRecommend
import com.example.appbanhang.R
import com.example.appbanhang.databinding.ActivityListChatBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListChatActivity : BaseActivity<ActivityListChatBinding>() {

    private lateinit var ds: ArrayList<DataUsers>
    private lateinit var userAdapter: AdapterListChat

    private lateinit var dbRef: DatabaseReference

    override val layoutId: Int
        get() = R.layout.activity_list_chat

    override fun setupUI() {
        super.setupUI()
        binding.apply {
            rcvListchat.setHasFixedSize(true)
            rcvListchat.layoutManager = LinearLayoutManager(this@ListChatActivity, RecyclerView.VERTICAL, false)
            ds = ArrayList()
            ds = arrayListOf<DataUsers>()


            dbRef = FirebaseDatabase.getInstance().getReference("Users")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ds = ArrayList<DataUsers>()
                    ds.clear()
                    if (snapshot.exists()) {
                        for (dataSnapshot: DataSnapshot in snapshot.children) {
                            val userName = dataSnapshot.child("userName").getValue(String::class.java)
                            val userId = dataSnapshot.child("userId").getValue(String::class.java)
                            if (userName != null) {
                                val empData = dataSnapshot.getValue(DataUsers::class.java)
                                val user = DataUsers(userName,userId)
                                ds.add(user)
                            }
                        }
                        userAdapter = AdapterListChat(ds)
                        rcvListchat.adapter = userAdapter

                        userAdapter.setonItemClickListener(object : AdapterListChat.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                val intent = Intent(this@ListChatActivity, ChatActivity::class.java)
                                intent.putExtra("userId", ds[position].UserID)
                                intent.putExtra("email", ds[position].email)
                                intent.putExtra("id", "case1")
                                startActivity(intent)
                            }
                        })
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}