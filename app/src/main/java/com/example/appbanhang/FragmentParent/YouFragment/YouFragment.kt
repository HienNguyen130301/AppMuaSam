package com.example.appbanhang.FragmentParent.YouFragment

import android.content.Intent
import android.widget.Toast
import com.example.appbanhang.Base.BaseFragment
import com.example.appbanhang.R
import com.example.appbanhang.Account.SignInActivity
import com.example.appbanhang.HelpActivity
import com.example.appbanhang.RateUsDialog
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
        binding.help.setOnClickListener{
            val intent = Intent(requireContext(), HelpActivity::class.java)
            startActivity(intent)
        }
        binding.rateUs.setOnClickListener {
            val rateUsDialog = RateUsDialog(requireContext())
            rateUsDialog.show()
        }
        binding.buying.setOnClickListener {
            Toast.makeText(requireContext(),"Come back next ver",Toast.LENGTH_SHORT).show()
        }
        binding.selling.setOnClickListener {
            Toast.makeText(requireContext(),"Come back next ver",Toast.LENGTH_SHORT).show()
        }
        binding.user.setOnClickListener {
            val intent1 = Intent(requireContext(),ChangePasswordActivity::class.java)
                startActivity(intent1)
        }
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(requireContext(), SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}