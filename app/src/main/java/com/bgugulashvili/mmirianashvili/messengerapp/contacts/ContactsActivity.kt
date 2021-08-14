package com.bgugulashvili.mmirianashvili.messengerapp.contacts

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.contacts.profile.ProfileFragment
import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB
import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.User
import com.bgugulashvili.mmirianashvili.messengerapp.shared.BottomNavigationController
import com.google.firebase.auth.FirebaseUser
import java.util.ArrayList

class ContactsActivity : AppCompatActivity(), IContactsView {

    private lateinit var currentUser: FirebaseUser

    private lateinit var mainViewPager: ViewPager2
    private lateinit var contactsFragment: Fragment
    private lateinit var profileFragment: Fragment
    private lateinit var fragments: ArrayList<Fragment>
    private lateinit var contactsViewPagerAdapter: ContactsViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        currentUser = AuthUtils.getCurrentUser()!!

        RealtimeDB.getInstance().userDao
            .getUser(currentUser.uid)
            .addOnSuccessListener {
                Log.i(AuthUtils.LOG_TAG, "User credentials: ${it.value}")
            }.addOnFailureListener {
                Log.e(AuthUtils.LOG_TAG, "Could not fetch user", it)
            }

        BottomNavigationController.init(findViewById(R.id.bottom_navigation_view))
        initView()
        initViewPager()
        initListeners()
    }

    override fun onSearch(users: List<User>) {
        TODO("Not yet implemented")
    }

    private fun initView() {
        mainViewPager = findViewById(R.id.main_view_pager)
    }

    private fun initViewPager() {
        contactsFragment = ContactsFragment(this)
        profileFragment = ProfileFragment(this)
        fragments = arrayListOf(contactsFragment, profileFragment)

        contactsViewPagerAdapter = ContactsViewPagerAdapter(this, fragments)
        mainViewPager.adapter = contactsViewPagerAdapter
    }

    private fun initListeners() {
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ContactsActivity::class.java))
        }
    }


}