package com.example.appbanhang

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.FirebaseApp
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.appbanhang.Data.DataRecommended
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.InputStream

class DangBaiActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var selectedImageUri: Uri

    private val selectImage: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedImageUri = it
                findViewById<ImageView>(R.id.nhapImg).apply {
                    setImageURI(selectedImageUri)
                    visibility = ImageView.VISIBLE
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dang_bai)
        FirebaseApp.initializeApp(this)
        dbRef = FirebaseDatabase.getInstance().getReference("ThemBaiDang")

        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)

        btn1.setOnClickListener {
            saveData()
        }
        btn2.setOnClickListener {
            selectImage.launch("image/*")
        }
    }

    private fun saveData() {
        val m1: EditText = findViewById(R.id.nhapDes)
        val m2: EditText = findViewById(R.id.nhapPrice)

        val mDes = m1.text.toString()
        val mPrice = m2.text.toString()

        val tkID = dbRef.push().key!!
        val themTK = DataRecommended(convertImageToBase64(selectedImageUri),mDes,mPrice)

        dbRef.child(tkID).setValue(themTK).addOnCompleteListener {
            Toast.makeText(this, "Post created successfully", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Error: $err", Toast.LENGTH_LONG).show()
        }
    }

    private fun convertImageToBase64(imageUri: Uri): String {
        val inputStream = contentResolver.openInputStream(imageUri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (inputStream?.read(buffer).also { bytesRead = it!! } != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead)
        }
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

}