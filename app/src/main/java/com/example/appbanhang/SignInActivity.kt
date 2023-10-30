package com.example.appbanhang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var client: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = Firebase.auth

        val sighUp : TextView = findViewById(R.id.signUp)
        val mLogin : Button = findViewById(R.id.login_btn)

        sighUp.setOnClickListener {
            gotoSignUp()
        }

        mLogin.setOnClickListener {
            Login()
        }

       /* if (auth.currentUser!= null ){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }*/



    }

    private fun gotoSignUp() {

        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun Login() {
        val edtEmail : EditText = findViewById(R.id.email)
        val edtPW : EditText = findViewById(R.id.password)

        val email = edtEmail.text.toString()
        val password = edtPW.text.toString()

        if (edtEmail.text.isEmpty() || edtPW.text.isEmpty()) {
            Toast.makeText(baseContext, "Please fill all the fields!", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {task ->
            if(task.isSuccessful) {
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)

                Toast.makeText(baseContext, "Login Susscessfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    baseContext,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,).show()
            }
        }
    }
}