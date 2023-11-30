package com.example.appbanhang.FragmentParent.HomeFragment.DangBai

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.R
import com.example.appbanhang.databinding.ActivityDangBaiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.ByteArrayOutputStream

class DangBaiActivity : BaseActivity<ActivityDangBaiBinding>() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var selectedImageUri: Uri

    override val layoutId: Int
        get() = R.layout.activity_dang_bai

    private val selectImage: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedImageUri = it
                binding.nhapImg.apply {
                    setImageURI(selectedImageUri)
                    visibility = ImageView.VISIBLE
                }
            }
        }

    override fun setupUI() {
        super.setupUI()

        binding.btn1.setOnClickListener {
            saveDataToDatabase()
        }
        binding.nhapImg.setOnClickListener {
            selectImage.launch("image/*")
        }
    }


    private fun saveDataToDatabase() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null) {
            val userId: String = firebaseUser.uid
            val userName = FirebaseDatabase.getInstance().getReference("Users").child(userId)
            Log.d("------", "saveDataToDatabase: $userName")
            userName.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val email = snapshot.child("userName").getValue(String::class.java)
                        if (email != null) {
                            Log.d("------", "onDataChange: $email")
                            val postRefUser = FirebaseDatabase.getInstance().getReference("YourPost").child(userId)
                            dbRef = FirebaseDatabase.getInstance().getReference("ThemBaiDang")

                            binding.apply {
                                val mTenSP = nhapTenSP.text.toString()
                                val mPrice = nhapPrice.text.toString()
                                val mDes = nhapDes.text.toString()
                                val selectedItem = chooseType.selectedItem.toString()
                                val isAvailable = true

                                val themTK = DataRecommended(
                                    convertImageToBase64(selectedImageUri),
                                    mTenSP,
                                    mPrice,
                                    mDes,
                                    selectedItem,
                                    isAvailable,
                                    userId,
                                    email)

                                val tkID = dbRef.push().key!!

                                val tkIDUser = postRefUser.push().key!!
                                postRefUser.child(tkIDUser).setValue(themTK)

                                dbRef.child(tkID).setValue(themTK).addOnCompleteListener {
                                    Toast.makeText(this@DangBaiActivity,"Data saved successfully",Toast.LENGTH_SHORT).show()
                                    finish()
                                }.addOnFailureListener {}
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
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