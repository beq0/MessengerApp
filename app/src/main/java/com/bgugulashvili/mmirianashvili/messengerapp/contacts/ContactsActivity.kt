package com.bgugulashvili.mmirianashvili.messengerapp.contacts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB

class ContactsActivity : AppCompatActivity() {

    private lateinit var signOutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        val username = intent.getStringExtra(USERNAME)!!

        RealtimeDB.getInstance().userDao
            .getUser(username)
            .addOnCompleteListener {
                Log.i(AuthUtils.LOG_TAG, "User credentials: ${it.result!!.value}")
            }.addOnFailureListener {
                Log.e(AuthUtils.LOG_TAG, "Could not fetch user", it)
            }

        initView()
        initListeners()
    }

    private fun initView() {
       signOutButton = findViewById(R.id.profile_sign_out)
    }

    private fun initListeners() {
        signOutButton.setOnClickListener {
            AuthUtils.signOutUser()
            finish()
        }
    }

    companion object {
        private const val USERNAME = "username"

        fun start(context: Context, username: String){
            context.startActivity(Intent(context, ContactsActivity::class.java).apply {
                putExtra(USERNAME, username)
            })
        }
    }
}