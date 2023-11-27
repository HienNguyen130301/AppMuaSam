package com.example.appbanhang.FragmentParent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appbanhang.Base.BaseFragment
import com.example.appbanhang.R
import com.example.appbanhang.SignInActivity
import com.example.appbanhang.databinding.FragmentYouBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class YouFragment : BaseFragment<FragmentYouBinding>() {

    override val layoutId: Int
        get() =R.layout.fragment_you

    override fun setupListener() {
        super.setupListener()

        binding.signOut1.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(requireContext(),"Sign Out Success",Toast.LENGTH_SHORT).show()
            navigateToLoginScreen()

        }
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(requireContext(), SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}