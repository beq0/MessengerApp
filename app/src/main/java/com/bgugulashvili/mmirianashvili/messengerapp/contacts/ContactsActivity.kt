package com.bgugulashvili.mmirianashvili.messengerapp.contacts

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.auth.login.LoginActivity
import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB
import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.User
import com.bgugulashvili.mmirianashvili.messengerapp.shared.BottomNavigationController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContactsActivity : AppCompatActivity(), IContactsView {

    private lateinit var signOutButton: Button
    private lateinit var etSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val username = intent.getStringExtra(USERNAME)!!

        RealtimeDB.getInstance().userDao
            .getUser(username)
            .addOnCompleteListener {
                Log.i(AuthUtils.LOG_TAG, "User credentials: ${it.result!!.value}")
            }.addOnFailureListener {
                Log.e(AuthUtils.LOG_TAG, "Could not fetch user", it)
            }

        BottomNavigationController.init(findViewById(R.id.bottom_navigation_view))
        initView()
        initListeners()

    }

    override fun onSearch(users: List<User>) {
        TODO("Not yet implemented")
    }

    private fun initView() {
        signOutButton = findViewById(R.id.profile_sign_out)
        etSearch = findViewById(R.id.contacts_search)
    }

    private fun initListeners() {
        signOutButton.setOnClickListener {
            AuthUtils.signOutUser()
            finish()
            LoginActivity.start(this)
        }
    }

    companion object {
        private const val USERNAME = "username"

        fun start(context: Context, username: String) {
            context.startActivity(Intent(context, ContactsActivity::class.java).apply {
                putExtra(USERNAME, username)
            })
        }
    }


}