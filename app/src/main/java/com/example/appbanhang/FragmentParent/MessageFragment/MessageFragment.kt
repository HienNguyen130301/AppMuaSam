package com.example.appbanhang.FragmentParent.MessageFragment

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appbanhang.Base.BaseFragment
import com.example.appbanhang.R
import com.example.appbanhang.databinding.FragmentMessageBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue


class MessageFragment : BaseFragment<FragmentMessageBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_message

    override fun setupUI() {
        super.setupUI()


    }
    private fun sendMessage(){

    }
}