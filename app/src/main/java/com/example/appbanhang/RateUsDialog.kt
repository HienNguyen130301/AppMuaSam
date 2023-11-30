package com.example.appbanhang

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class RateUsDialog(context: Context) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_rate_us)
        setCancelable(true)
        setCanceledOnTouchOutside(true)


        val btnRateNow = findViewById<Button>(R.id.btnRateNow)
        val btnRemindLater = findViewById<Button>(R.id.btnRemindLater)
        val btnNoThanks = findViewById<Button>(R.id.btnNoThanks)

        btnRateNow.setOnClickListener {
            Toast.makeText(context,"Thank you so much",Toast.LENGTH_SHORT).show()
            dismiss()
        }

        btnRemindLater.setOnClickListener {
            // Handle remind later action
            dismiss()
        }

        btnNoThanks.setOnClickListener {
            // Handle no thanks action
            dismiss()
        }
    }

    private fun openAppInPlayStore() {
        val appPackageName = context.packageName
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (e: android.content.ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }
}
