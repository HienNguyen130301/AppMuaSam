package com.example.appbanhang.Account

import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.Data.DataUsers
import com.example.appbanhang.R
import com.example.appbanhang.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override val layoutId: Int
        get() = R.layout.activity_sign_up

    override fun setupUI() {
        super.setupUI()

        auth = Firebase.auth
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        binding.signUpBtn.setOnClickListener {
            SignUp()
        }
    }

    private fun SignUp() {

        binding.apply {
            val email = email1.text.toString()
            val password = password1.text.toString()

            if (email1.text.isEmpty() || password1.text.isEmpty()) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@SignUpActivity) { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        val userId: String = user!!.uid

                        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userId)

                        val tkID = dbRef.push().key!!

                        val hashMap: HashMap<String, String> = HashMap()
                        hashMap.put("userId", userId)
                        hashMap.put("userName", email)

                        dbRef.setValue(hashMap).addOnCompleteListener {
                            finish()
                        }.addOnFailureListener { err ->
                        }
                        Toast.makeText(baseContext, "Susscess, Please Login", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                Toast.makeText(this@SignUpActivity, "Da co loi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}