package com.example.appbanhang.FragmentParent.SearchFragment

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.Data.DataRecommended
import com.example.appbanhang.R
import com.example.appbanhang.databinding.ActivityDetailCateBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailActivityCate : BaseActivity<ActivityDetailCateBinding>() {

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
            val base64Image = convertImageToBase64(selectedImageUri)

            // Update the "imageUrl" field in Firebase
            updateImageToFirebase(base64Image)
        }

    override fun setupUI() {
        super.setupUI()

        binding.imageDetail1.setOnClickListener {
            selectImage.launch("image/*")
        }

        ds1 = arrayListOf<DataRecommended>()

        val bundle : Bundle?= intent.extras
        val key = bundle?.getString("key")
        dbRef =FirebaseDatabase.getInstance().getReference("ThemBaiDang").child(key!!)
        dbRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val dataRecommended = snapshot.getValue(DataRecommended::class.java)
                    if (dataRecommended != null){
                        binding.txtTenSp.text = dataRecommended.tenSP
                        binding.txtGia.text = dataRecommended.price
                        binding.txtDes.text = dataRecommended.des
                        binding.txtType.text = dataRecommended.type

                        if (dataRecommended.imageUrl != null) {
                            val decodedBytes = Base64.decode(dataRecommended.imageUrl, Base64.DEFAULT)
                            val decodedBitmap =
                                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                            binding.imageDetail.setImageBitmap(decodedBitmap)
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.Luu.setOnClickListener {
            val newTenSP = binding.txtTenSp1.text.toString()
            val newGiaSP = binding.txtGia1.text.toString()
            val newDesSP = binding.txtDes1.text.toString()
            val newTypeSP = binding.txtType1.text.toString()
            updateDataToFirebase(newTenSP,newGiaSP,newDesSP,newTypeSP)
        }
    }

    private fun updateDataToFirebase(
        newTenSP: String,
        newGiaSP: String,
        newDesSP: String,
        newTypeSP: String
    ) {
        val bundle : Bundle?= intent.extras
        val key = bundle?.getString("key")
        val dbRef = FirebaseDatabase.getInstance().getReference("ThemBaiDang").child(key!!)
        dbRef.child("tenSP").setValue(newTenSP)
        dbRef.child("price").setValue(newGiaSP)
        dbRef.child("des").setValue(newDesSP)
        dbRef.child("type").setValue(newTypeSP)
        dbRef.child("imageUrl").setValue(imageUrl)
    }
    private fun convertImageToBase64(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}
