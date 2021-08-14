package com.bgugulashvili.mmirianashvili.messengerapp.contacts.profile

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.auth.login.LoginActivity
import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.User
import com.google.android.material.button.MaterialButton

class ProfileFragment(private var parentActivity: Activity) : Fragment(), IProfileView {

    private lateinit var presenter: IProfilePresenter

    private lateinit var currView: View

    private lateinit var etUsername: EditText
    private lateinit var etProfession: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnLogout: MaterialButton

    private lateinit var currentUsername: String
    private lateinit var currentProfession: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        currView = inflater.inflate(R.layout.fragment_profile, container, false)
        presenter = ProfilePresenter(this)
        initView()
        initListeners()
        presenter.fetchProfile()
        return currView
    }

    override fun onUpdate(username: String, profession: String) {
        currentUsername = username
        currentProfession = profession
        Toast.makeText(parentActivity, "Profile Successfully Updated!", Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateFailed() {
        Toast.makeText(parentActivity, "Profile Update Failed!", Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateRequiresAuthentication() {
        Toast.makeText(parentActivity, "Please re-login to update username", Toast.LENGTH_SHORT).show()
    }

    override fun onSignOut() {
        AuthUtils.signOutUser()
        parentActivity.finish()
        LoginActivity.start(parentActivity)
    }

    override fun onProfileFetched(user: User) {
        currentUsername = user.username!!
        currentProfession = user.profession!!
        etUsername.setText(user.username)
        etProfession.setText(user.profession)
    }

    override fun onProfileFetchFailed() {
        Toast.makeText(parentActivity, "Could not load profile!", Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        etUsername = currView.findViewById(R.id.profile_name)
        etProfession = currView.findViewById(R.id.profile_profession)
        btnUpdate = currView.findViewById(R.id.profile_update)
        btnLogout = currView.findViewById(R.id.profile_logout)
    }

    private fun initListeners() {
        btnUpdate.setOnClickListener {
            val newUsername = etUsername.text.toString()
            val newProfession = etProfession.text.toString()
            if (isValidForUpdate(newUsername, newProfession)) {
                presenter.update(newUsername, newProfession)
            } else {
                Toast.makeText(parentActivity, "Nothing to Update!", Toast.LENGTH_SHORT).show()
            }
        }
        btnLogout.setOnClickListener {
            onSignOut()
        }
    }

    private fun isValidForUpdate(username: String, profession: String) : Boolean {
        return currentUsername != username || currentProfession != profession
    }
}