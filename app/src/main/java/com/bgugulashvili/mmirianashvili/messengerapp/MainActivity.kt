package com.bgugulashvili.mmirianashvili.messengerapp

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: MaterialButton

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            userLoggedIn(currentUser)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initView()
        Log.i("dsa", "dsadsa")

        auth = Firebase.auth

        btnLogin.setOnClickListener {
            loginUser(
                etUsername.text.toString() + AuthConst.EMAIL_SUFFIX,
                etPassword.text.toString()
            )
        }
        btnRegister.setOnClickListener {
            registerUser(
                etUsername.text.toString() + AuthConst.EMAIL_SUFFIX,
                etPassword.text.toString()
            )
        }


    }

    private fun initView() {
        etUsername = findViewById(R.id.login_nickname)
        etPassword = findViewById(R.id.login_password)
        btnLogin = findViewById(R.id.login_button)
        btnRegister = findViewById(R.id.login_register_button)
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(this, "Logged in: ${user?.email}", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Login failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(this, "Registered: ${user?.email}", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(AuthConst.LOG_TAG, task.exception.toString())
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Registration failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun userLoggedIn(user: FirebaseUser) {
        Log.i(AuthConst.LOG_TAG, "User already logged in: ${user.email}")
        Toast.makeText(this, "User already Logged in: ${user.email}", Toast.LENGTH_SHORT).show()
    }
}