package com.bgugulashvili.mmirianashvili.messengerapp.usersearch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB
import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.user.User
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class UserSearchActivity : AppCompatActivity(), IUserSearchView, UserSearchUserClickListener {

    private lateinit var toolbar: Toolbar
    private lateinit var etSearch: EditText
    private lateinit var btnBack: ImageView
    private lateinit var rvUsers: RecyclerView
    private lateinit var rvAdapter: UserSearchUserAdapter

    private lateinit var presenter: IUserSearchPresenter

    private var searchTimer: ScheduledFuture<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search)

        presenter = UserSearchPresenter(this)
        initView()
        initListeners()
        presenter.initialize()
    }

    override fun onInitial(users: ArrayList<User>) {
        rvAdapter = UserSearchUserAdapter(
            ArrayList(users.map { u -> UserSearchUserItem(u.uid!!, u.username!!, u.profession!!) })
        )
        rvAdapter.clickListener = this
        rvUsers.adapter = rvAdapter
    }

    override fun onSearch(users: ArrayList<User>) {
        rvAdapter.updateData(
            ArrayList(users.map { u -> UserSearchUserItem(u.uid!!, u.username!!, u.profession!!) })
        )
    }

    private fun initView() {
        toolbar = findViewById(R.id.user_search_toolbar)
        etSearch = findViewById(R.id.user_search_search)
        btnBack = findViewById(R.id.user_search_back_btn)
        rvUsers = findViewById(R.id.user_search_users)
    }

    private fun initListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        etSearch.doAfterTextChanged {
            val username = etSearch.text.toString()
            if (username.length >= MIN_SEARCH_CHARS) {
                searchTimer?.cancel(true)
                searchTimer = Executors.newSingleThreadScheduledExecutor().schedule({
                    presenter.searchUsers(username)
                }, TEXT_CHANGE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            } else {
                searchTimer?.cancel(true)
                searchTimer = Executors.newSingleThreadScheduledExecutor().schedule({
                    presenter.searchUsers()
                }, TEXT_CHANGE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            }
        }
    }

    override fun onUserClicked(item: UserSearchUserItem) {
        RealtimeDB.getInstance().mesDao.addMessage(
            AuthUtils.getCurrentUserUid(), AuthUtils.getCurrentUserUsername(),
            item.uid, item.name, "mesiji ageraa"
        )
        Toast.makeText(this, "User: ${item.name} clicked", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val MIN_SEARCH_CHARS = 3
        private const val TEXT_CHANGE_TIMEOUT_MILLIS = 1500L
        fun start(context: Context) {
            context.startActivity(Intent(context, UserSearchActivity::class.java))
        }
    }

}