package com.example.appbanhang

import android.content.pm.ActivityInfo
import androidx.fragment.app.Fragment
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.FragmentParent.HomeFragment.HomeFragment
import com.example.appbanhang.FragmentParent.SearchFragment.SearchFragment
import com.example.appbanhang.FragmentParent.YouFragment.YouFragment
import com.example.appbanhang.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun setupUI() {
        super.setupUI()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        replaceFragment(HomeFragment())

        binding.BottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.wallet -> replaceFragment(SearchFragment())
                R.id.more -> replaceFragment(YouFragment())

                else -> {

                }
            }
            true
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTrans = fragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fl1, fragment)
        fragmentTrans.commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (shouldPreventBack()) {
        } else {
            super.onBackPressed()
        }
    }
    private fun shouldPreventBack(): Boolean {
        return true
    }
}