package com.example.appbanhang.FragmentParent.SearchFragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.MainActivity
import com.example.appbanhang.R
import com.example.appbanhang.databinding.ActivityDetailCateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailSearchFragment : BaseActivity<ActivityDetailCateBinding>() {

    private lateinit var ds1: ArrayList<DataRecommended>
    private lateinit var dbRef: DatabaseReference
    private lateinit var selectedImageUri: Uri

    override val layoutId: Int
        get() = R.layout.activity_detail_cate

    private val selectImage: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedImageUri = it
                binding.imageDetail1.apply {
                    setImageURI(selectedImageUri)
                    visibility = ImageView.VISIBLE
                }
            }
        }

    override fun setupUI() {
        super.setupUI()



        binding.imageDetail1.setOnClickListener {
            selectImage.launch("image/*")
        }

        ds1 = arrayListOf<DataRecommended>()

        val bundle: Bundle? = intent.extras
        val key = bundle?.getString("key")
        Log.d("hienzd", "setupUI: $key")
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser?.uid
        dbRef = FirebaseDatabase.getInstance().getReference("YourPost").child(userId!!).child(key!!)
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val dataRecommended = snapshot.getValue(DataRecommended::class.java)
                    if (dataRecommended != null) {
                        binding.txtTenSp.text = dataRecommended.tenSP
                        binding.txtGia.text = dataRecommended.price
                        binding.txtDes.text = dataRecommended.des
                        binding.txtType.text = dataRecommended.type
                        binding.txtTrangThai.text = dataRecommended.isAvailable.toString()

                        if (dataRecommended.imageUrl != null) {
                            val decodedBytes =
                                Base64.decode(dataRecommended.imageUrl, Base64.DEFAULT)
                            val decodedBitmap =
                                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                            binding.imageDetail.setImageBitmap(decodedBitmap)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        binding.txtTrangThai1.setOnCheckedChangeListener { buttonView, isChecked ->
            val newAvailable = isChecked.toString()
            Log.d("Checkbox", "New Available: $newAvailable")
        }
        binding.Luu.setOnClickListener {
            val newTenSP = binding.txtTenSp1.text.toString()
            val newGiaSP = binding.txtGia1.text.toString()
            val newDesSP = binding.txtDes1.text.toString()
            val newTypeSP = binding.txtType1.text.toString()

            val newAvailable = binding.txtTrangThai1.isChecked
            if (newTenSP.isEmpty() || newGiaSP.isEmpty() || newDesSP.isEmpty() || newTypeSP.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!::selectedImageUri.isInitialized) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val base64Image = convertImageToBase64(selectedImageUri)
            updateDataToFirebase(newTenSP, newGiaSP, newDesSP, newTypeSP,newAvailable,base64Image)
                Toast.makeText(this,"Change Data Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
        }

        binding.xoa.setOnClickListener {
            val dbRefYourPost = FirebaseDatabase.getInstance().getReference("YourPost").child(userId).child(key)
            dbRefYourPost.removeValue()
            val dbRefThemBaiDang = FirebaseDatabase.getInstance().getReference("ThemBaiDang").child(key)
            dbRefThemBaiDang.removeValue()
            Toast.makeText(this,"Delete Data Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateDataToFirebase(
        newTenSP: String,
        newGiaSP: String,
        newDesSP: String,
        newTypeSP: String,
        newAvailable: Boolean,
        base64Image: String
    ) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId: String = firebaseUser!!.uid
        val userName = FirebaseDatabase.getInstance().getReference("Users").child(userId)
        userName.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val email = snapshot.child("userName").getValue(String::class.java)

                    val bundle: Bundle? = intent.extras
                    val key = bundle?.getString("key")
                    val dbRef = FirebaseDatabase.getInstance().getReference("YourPost").child(userId).child(key!!)
                    dbRef.child("imageUrl").setValue(base64Image)
                    dbRef.child("tenSP").setValue(newTenSP)
                    dbRef.child("price").setValue(newGiaSP)
                    dbRef.child("des").setValue(newDesSP)
                    dbRef.child("type").setValue(newTypeSP)
                    dbRef.child("available").setValue(newAvailable)
                    dbRef.child("userID").setValue(userId)
                    dbRef.child("userName").setValue(email)

                    val dbRefThemBaiDang = FirebaseDatabase.getInstance().getReference("ThemBaiDang").child(key)
                    dbRefThemBaiDang.child("imageUrl").setValue(convertImageToBase64(selectedImageUri))
                    dbRefThemBaiDang.child("tenSP").setValue(newTenSP)
                    dbRefThemBaiDang.child("price").setValue(newGiaSP)
                    dbRefThemBaiDang.child("des").setValue(newDesSP)
                    dbRefThemBaiDang.child("type").setValue(newTypeSP)
                    dbRefThemBaiDang.child("available").setValue(newAvailable)
                    dbRefThemBaiDang.child("userID").setValue(userId)
                    dbRefThemBaiDang.child("userName").setValue(email)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun convertImageToBase64(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

}
