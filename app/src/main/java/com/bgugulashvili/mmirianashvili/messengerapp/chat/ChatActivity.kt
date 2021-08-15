package com.bgugulashvili.mmirianashvili.messengerapp.chat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bgugulashvili.mmirianashvili.messengerapp.R
import android.view.MotionEvent

import android.view.View.OnTouchListener




class ChatActivity : AppCompatActivity(), IChatView {

    private lateinit var uid: String

    private lateinit var tvUsername: TextView
    private lateinit var tvProfession: TextView
    private lateinit var rvChat: RecyclerView
    private lateinit var rvAdapter: ChatAdapter
    private lateinit var etChatText: EditText
    private lateinit var btnBack: ImageView

    private lateinit var presenter: IChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        uid = intent.getStringExtra(UID)!!
        presenter = ChatPresenter(this)
        initView()
        initListeners()
        initAdapter()
        presenter.loadChat(uid)
    }

    @Synchronized override fun onChatItemLoaded(item: ChatItem) {
        if (rvAdapter.list.isNullOrEmpty()) {
            rvAdapter.list = arrayListOf(item)
            rvAdapter.notifyDataSetChanged()
        } else {
            rvAdapter.insertData(item)
        }
    }

    override fun onUserLoaded(username: String, profession: String) {
        tvUsername.text = username
        tvProfession.text = profession
    }

    private fun initView() {
        tvUsername = findViewById(R.id.chat_name)
        tvProfession = findViewById(R.id.chat_profession)
        rvChat = findViewById(R.id.chat_chat_rv)
        etChatText = findViewById(R.id.chat_txt)
        btnBack = findViewById(R.id.chat_back_btn)
    }

    private fun initListeners() {
        btnBack.setOnClickListener {
            finish()
        }
//        etChatText.setOnTouchListener(OnTouchListener { v, event ->
//            val DRAWABLE_LEFT = 0
//            val DRAWABLE_TOP = 1
//            val DRAWABLE_RIGHT = 2
//            val DRAWABLE_BOTTOM = 3
//            if (event.action == MotionEvent.ACTION_UP) {
//                if (event.rawX >= etChatText.getRight() - etChatText.getCompoundDrawables()
//                        .get(DRAWABLE_RIGHT).getBounds().width()
//                ) {
//
//                }
//            }
//            false
//        })
    }

    private fun initAdapter() {
        rvAdapter = ChatAdapter(arrayListOf())
        rvChat.adapter = rvAdapter
    }

    companion object {
        const val UID = "uid"
        fun start(context: Context, username: String) {
            context.startActivity(Intent(context, ChatActivity::class.java).apply {
                putExtra(UID, username)
            })
        }
    }
}