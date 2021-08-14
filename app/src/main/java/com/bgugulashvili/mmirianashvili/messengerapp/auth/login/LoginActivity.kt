package com.bgugulashvili.mmirianashvili.messengerapp.auth.login

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.auth.register.RegisterActivity
import com.bgugulashvili.mmirianashvili.messengerapp.contacts.ContactsActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity(), ILoginView {

    private lateinit var presenter: ILoginPresenter

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: MaterialButton

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = AuthUtils.getCurrentUser()
        if (currentUser != null) {
            userAlreadyAuthenticated(currentUser)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        presenter = LoginPresenter(this)
        initView()
        initListeners()

    }

    override fun authenticationSuccessful(user: FirebaseUser) {
        Toast.makeText(this, "Logged in: ${user.email}", Toast.LENGTH_SHORT).show()
        finish()
        ContactsActivity.start(this)
    }

    override fun authenticationFailed() {
        Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        etUsername = findViewById(R.id.login_nickname)
        etPassword = findViewById(R.id.login_password)
        btnLogin = findViewById(R.id.login_button)
        btnRegister = findViewById(R.id.login_register_button)
    }

    private fun initListeners() {
        btnLogin.setOnClickListener {
            presenter.onUserCheckAuthentication(etUsername.text.toString(), etPassword.text.toString())
        }
        btnRegister.setOnClickListener {
            RegisterActivity.start(this)
        }
    }

    private fun userAlreadyAuthenticated(user: FirebaseUser) {
        Log.i(AuthUtils.LOG_TAG, "User already logged in: ${user.email}")
        Toast.makeText(this, "User already Logged in: ${user.email}", Toast.LENGTH_SHORT).show()

        finish()
        ContactsActivity.start(this)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

}