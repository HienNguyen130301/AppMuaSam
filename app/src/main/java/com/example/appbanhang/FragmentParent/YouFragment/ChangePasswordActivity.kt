package com.example.appbanhang.FragmentParent.YouFragment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appbanhang.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var editTextOldPassword: EditText
    private lateinit var editTextNewPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var btnChangePassword: Button
    private lateinit var textViewUserEmail: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        auth = FirebaseAuth.getInstance()

        editTextOldPassword = findViewById(R.id.editTextOldPassword)
        editTextNewPassword = findViewById(R.id.editTextNewPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        btnChangePassword = findViewById(R.id.btnChangePassword)
        textViewUserEmail = findViewById(R.id.textViewUserEmail)

        btnChangePassword.setOnClickListener {
            changePassword()
        }

        val currentUser = auth.currentUser
        textViewUserEmail.text = "User Email: ${currentUser?.email}"
    }

    private fun changePassword() {
        val user: FirebaseUser? = auth.currentUser
        val oldPassword = editTextOldPassword.text.toString()
        val newPassword = editTextNewPassword.text.toString()
        val confirmPassword = editTextConfirmPassword.text.toString()

        if (user != null && user.email != null) {
            // Re-authenticate user
            val credential = EmailAuthProvider
                .getCredential(user.email!!, oldPassword)

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Passwords match, change the password
                        if (newPassword == confirmPassword) {
                            user.updatePassword(newPassword)
                                .addOnCompleteListener { passwordUpdateTask ->
                                    if (passwordUpdateTask.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Password updated successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Failed to update password",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(
                                this,
                                "New passwords do not match",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Old password is incorrect",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
