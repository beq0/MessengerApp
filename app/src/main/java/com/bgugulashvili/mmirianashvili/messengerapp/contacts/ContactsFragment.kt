package com.bgugulashvili.mmirianashvili.messengerapp.contacts

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.chat.ChatActivity
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class ContactsFragment(private var parentActivity: Activity) : Fragment(), IContactsView, ContactListItemClickListener {

    private lateinit var currView: View
    private lateinit var etSearch: EditText
    private lateinit var rvContacts: RecyclerView
    private lateinit var rvAdapter: ContactsListAdapter

    private lateinit var presenter: IContactsPresenter
    private var isInitialized: Boolean = false

    private var searchTimer: ScheduledFuture<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        currView = inflater.inflate(R.layout.fragment_contacts, container, false)
        presenter = ContactsPresenter(this)
        initView()
        initListeners()
        initializeAdapter()
        presenter.searchUsers()
        return currView
    }

    override fun onResume() {
        super.onResume()
        if (isInitialized) {
            onSearch()
        } else {
            isInitialized = true
        }
    }

    override fun onSearch(username: String?) {
        rvAdapter.removeData()
        presenter.searchUsers(username)
    }

    @Synchronized override fun onContactFound(contact: ContactsListItem) {
        if (rvAdapter.list.isNullOrEmpty()) {
            rvAdapter.list = arrayListOf(contact)
            rvAdapter.notifyDataSetChanged()
        } else {
            rvAdapter.insertData(contact)
        }
    }

    private fun initView() {
        etSearch = currView.findViewById(R.id.contacts_search)
        rvContacts = currView.findViewById(R.id.contacts_list)
    }

    private fun initListeners() {
        etSearch.doAfterTextChanged {
            val username = etSearch.text.toString()
            if (username.length >= MIN_SEARCH_CHARS) {
                searchTimer?.cancel(true)
                searchTimer = Executors.newSingleThreadScheduledExecutor().schedule({
                    onSearch(username)
                }, TEXT_CHANGE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            } else {
                searchTimer?.cancel(true)
                searchTimer = Executors.newSingleThreadScheduledExecutor().schedule({
                    onSearch()
                }, TEXT_CHANGE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            }
        }
    }

    private fun initializeAdapter() {
        rvAdapter = ContactsListAdapter(arrayListOf())
        rvAdapter.clickListener = this
        rvContacts.adapter = rvAdapter
    }

    override fun onContactClicked(item: ContactsListItem) {
//        Toast.makeText(parentActivity, "Contact ${item.name}", Toast.LENGTH_SHORT).show()
        ChatActivity.start(parentActivity, item.toUid)
    }

    companion object {
        private const val MIN_SEARCH_CHARS = 3
        private const val TEXT_CHANGE_TIMEOUT_MILLIS = 1500L
    }

}