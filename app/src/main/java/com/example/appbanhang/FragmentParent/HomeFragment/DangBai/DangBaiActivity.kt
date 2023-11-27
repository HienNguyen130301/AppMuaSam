package com.example.appbanhang.FragmentParent.HomeFragment.DangBai

import android.net.Uri
import android.util.Base64
import android.util.Log
import com.google.firebase.FirebaseApp
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.DataBase.ItemEntity
import com.example.appbanhang.R
import com.example.appbanhang.databinding.ActivityDangBaiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.UUID
import kotlin.math.log

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
            val userId = firebaseUser.uid

            val currentUser = FirebaseAuth.getInstance().currentUser
            var username: String? = null

            if (currentUser != null) {
                val uid = currentUser.uid

                val usersRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)
                usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val email = snapshot.child("email").getValue(String::class.java)
                            if (email != null) {
                                username = email
                                println(email)
                                println()
                                Log.d("hehe", "email:$email ")
                                Log.d("hehe", "username: $username")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle error
                        Log.e("EmailValue", "Database error: ${error.message}")
                    }
                })
            }


            val postRefUser = FirebaseDatabase.getInstance().getReference("YourPost").child(userId)
            dbRef = FirebaseDatabase.getInstance().getReference("ThemBaiDang")
            val RefSneaker = FirebaseDatabase.getInstance().getReference("Sneaker")
            val RefShoes = FirebaseDatabase.getInstance().getReference("Shoes")
            val RefApparel = FirebaseDatabase.getInstance().getReference("Apparel")
            val RefElectronics = FirebaseDatabase.getInstance().getReference("Electronics")
            val RefAccessorie = FirebaseDatabase.getInstance().getReference("Accessorie")

            binding.apply {
                val mTenSP = nhapTenSP.text.toString()
                val mPrice = nhapPrice.text.toString()
                val mDes = nhapDes.text.toString()
                val selectedItem = chooseType.selectedItem.toString()
                val userId = FirebaseDatabase.getInstance().getReference("ThemBaiDang").key
                val userName1 = username

                val themTK = DataRecommended(convertImageToBase64(selectedImageUri),mTenSP,mPrice,mDes,selectedItem,userName1,userId)

                val tkID = dbRef.push().key!!

                if (themTK.type == "Sneaker") {
                    val tkIDUser = RefSneaker.push().key!!
                    RefSneaker.child(tkIDUser).setValue(themTK)
                }
                if (themTK.type == "Shoes") {
                    val tkIDUser = RefShoes.push().key!!
                    RefShoes.child(tkIDUser).setValue(themTK)
                }
                if (themTK.type == "Apparel") {
                    val tkIDUser = RefApparel.push().key!!
                    RefApparel.child(tkIDUser).setValue(themTK)
                }
                if (themTK.type == "Electronics") {
                    val tkIDUser = RefElectronics.push().key!!
                    RefElectronics.child(tkIDUser).setValue(themTK)
                }
                if (themTK.type == "Accessorie") {
                    val tkIDUser = RefAccessorie.push().key!!
                    RefAccessorie.child(tkIDUser).setValue(themTK)
                }

                val tkIDUser = postRefUser.push().key!!
                postRefUser.child(tkIDUser).setValue(themTK)

                dbRef.child(tkID).setValue(themTK).addOnCompleteListener {
                    Toast.makeText(
                        this@DangBaiActivity,
                        "Data saved successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }.addOnFailureListener {}
            }
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