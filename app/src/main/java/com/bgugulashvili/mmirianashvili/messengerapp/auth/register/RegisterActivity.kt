package com.bgugulashvili.mmirianashvili.messengerapp.auth.register

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.contacts.ContactsActivity
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity(), IRegisterView {

    private lateinit var registerPresenter: IRegisterPresenter

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etProfession: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        registerPresenter = RegisterPresenter(this)
        initView()
        initListeners()
    }

    override fun registrationSuccessful(user: FirebaseUser) {
        Toast.makeText(this, "Registered: ${user.email}", Toast.LENGTH_SHORT).show()
        finish()
        ContactsActivity.start(this)
    }

    override fun registrationFailed() {
        Toast.makeText(baseContext, "Registration failed.", Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        etUsername = findViewById(R.id.register_nickname)
        etPassword = findViewById(R.id.register_password)
        etProfession = findViewById(R.id.register_profession)
        btnRegister = findViewById(R.id.register_button)
    }

    private fun initListeners() {
        btnRegister.setOnClickListener {
            registerPresenter.onUserRegister(
                etUsername.text.toString(), etPassword.text.toString(), etProfession.text.toString()
            )
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }


}