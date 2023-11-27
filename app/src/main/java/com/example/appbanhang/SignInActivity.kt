package com.example.appbanhang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.appbanhang.Base.BaseActivity
import com.example.appbanhang.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : BaseActivity<ActivitySignInBinding>() {

    private lateinit var auth: FirebaseAuth
    private lateinit var client: GoogleSignInClient

    override val layoutId: Int
        get() = R.layout.activity_sign_in

       /* if (auth.currentUser!= null ){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }*/
    override fun setupUI() {
        super.setupUI()

        auth = Firebase.auth
        binding.apply {
            signUp.setOnClickListener {
                gotoSignUp()
            }
            loginBtn.setOnClickListener {
                Login()
            }
        }
    }

    private fun gotoSignUp() {
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun Login() {

        binding.apply {
            val emailString = email.text.toString()
            val passwordString = password.text.toString()

            if (email.text.isEmpty() || password.text.isEmpty()) {
                Toast.makeText(baseContext, "Please fill all the fields!", Toast.LENGTH_SHORT).show()
                return
            }
            auth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener() {task ->
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
}