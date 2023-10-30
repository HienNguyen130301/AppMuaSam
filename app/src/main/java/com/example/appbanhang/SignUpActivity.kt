package com.example.appbanhang

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.appbanhang.Data.DataUsers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        val mSignUp: Button = findViewById(R.id.signUp_btn)
        mSignUp.setOnClickListener {
            SignUp()
        }

    }

    private fun SignUp() {
        val edtEmail = findViewById<EditText>(R.id.email1)
        val edtPw = findViewById<EditText>(R.id.password1)

        val email = edtEmail.text.toString()
        val password = edtPw.text.toString()

        if (edtEmail.text.isEmpty() || edtPw.text.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                //val intent1 = Intent(this, SignInActivity::class.java)
               // startActivity(intent1)

        /*        val user = auth.currentUser

                val hashMap = hashMapOf("userID" to user!!.uid,
                "userName" to  email,
                "pw" to password)

                val db = Firebase.firestore

                db.collection("Users").document(user.uid).set(hashMap)
                startActivity(Intent(this, SignInActivity::class.java))*/

                val tkID = dbRef.push().key!!
                val themUsers = DataUsers(email,password)

                dbRef.child(tkID).setValue(themUsers).addOnCompleteListener {
                    Toast.makeText(this, "Post created successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error: $err", Toast.LENGTH_LONG).show()
                }

                Toast.makeText(baseContext, "Susscess, Please Login", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
                Toast.makeText(this, "Da co loi", Toast.LENGTH_SHORT).show()
            }
    }

}