package com.bgugulashvili.mmirianashvili.messengerapp.contacts

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.auth.login.LoginActivity

class ContactsFragment(parentActivity: Activity) : Fragment() {

    private lateinit var parentActivity: Activity
    private lateinit var currView: View
    private lateinit var signOutButton: Button
    private lateinit var etSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        currView = inflater.inflate(R.layout.fragment_contacts, container, false)
        initView()
        initListeners()
        return currView
    }

    private fun initView() {
        signOutButton = currView.findViewById(R.id.profile_sign_out)
        etSearch = currView.findViewById(R.id.contacts_search)
    }

    private fun initListeners() {
        signOutButton.setOnClickListener {
            AuthUtils.signOutUser()
            parentActivity.finish()
            LoginActivity.start(parentActivity)
        }
    }

}