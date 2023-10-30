package com.example.appbanhang

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.appbanhang.FragmentParent.HomeFragment
import com.example.appbanhang.FragmentParent.MessageFragment
import com.example.appbanhang.FragmentParent.SearchFragment
import com.example.appbanhang.FragmentParent.YouFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mBottomView: BottomNavigationView = findViewById(R.id.BottomNavView)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        replaceFragment(HomeFragment())

        mBottomView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.wallet -> replaceFragment(SearchFragment())
                R.id.chart -> replaceFragment(MessageFragment())
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

    override fun onBackPressed() {
        super.onBackPressed()
        // Do nothing to prevent going back to the logged-in state
    }

}