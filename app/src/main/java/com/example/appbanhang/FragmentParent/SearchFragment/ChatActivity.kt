package com.example.appbanhang.FragmentParent.SearchFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appbanhang.Adapter.AdapterChat
import com.example.appbanhang.Adapter.AdapterListChat
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.Data.Chat
import com.example.appbanhang.Data.DataChat
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.Data.DataUsers
import com.example.appbanhang.R
import com.example.appbanhang.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : BaseActivity<ActivityChatBinding>() {

    private lateinit var ds1: ArrayList<Chat>
    private lateinit var ds: ArrayList<DataUsers>
    private lateinit var dbRef: DatabaseReference
    private lateinit var userName1: String

    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var chatList = java.util.ArrayList<Chat>()
    var topic = ""

    override val layoutId: Int
        get() = R.layout.activity_chat

    override fun setupUI() {
        super.setupUI()

        ds1 = arrayListOf<Chat>()
        binding.chatRecyclerView.setHasFixedSize(true)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        ds1 = ArrayList()
        ds1 = arrayListOf<Chat>()

        val bundle: Bundle? = intent.extras
        val userId = bundle?.getString("userId")
        val email = bundle?.getString("email")
        val userIdItem = bundle?.getString("id")
        Log.d("------", "UserIDDetail: $userIdItem")

        if(userIdItem == "case1") {
            binding.tvUserName.text = email
            firebaseUser = FirebaseAuth.getInstance().currentUser

            binding.btnSendMessage.setOnClickListener {
                var message: String = binding.etMessage.text.toString()

                if (message.isEmpty()) {
                    binding.etMessage.setText("")
                } else {
                    sendMessage(firebaseUser!!.uid, userId!!, message)
                    binding.etMessage.setText("")
                    topic = "/topics/$userId"
                }
            }
            readMessage(firebaseUser!!.uid, userId!!)
        } else {
            binding.tvUserName.text = email
            firebaseUser = FirebaseAuth.getInstance().currentUser

            binding.btnSendMessage.setOnClickListener {
                var message: String = binding.etMessage.text.toString()

                if (message.isEmpty()) {
                    binding.etMessage.setText("")
                } else {
                    sendMessage(firebaseUser!!.uid, userId!!, message)
                    binding.etMessage.setText("")
                    topic = "/topics/$userId"
                }
            }
            readMessage(firebaseUser!!.uid, userId!!)
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }


    }
    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)
        reference!!.child("Chat").push().setValue(hashMap)
    }

    fun readMessage(senderId: String, receiverId: String) {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(Chat::class.java)

                    if (chat!!.senderId.equals(senderId) && chat!!.receiverId.equals(receiverId) ||
                        chat!!.senderId.equals(receiverId) && chat!!.receiverId.equals(senderId)
                    ) {
                        chatList.add(chat)
                    }
                }

                val chatAdapter = AdapterChat(chatList)
                binding.chatRecyclerView.adapter = chatAdapter
            }
        })
    }
}