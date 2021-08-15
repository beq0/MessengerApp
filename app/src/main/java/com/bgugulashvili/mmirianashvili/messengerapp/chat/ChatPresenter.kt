package com.bgugulashvili.mmirianashvili.messengerapp.chat

import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB
import com.bgugulashvili.mmirianashvili.messengerapp.data.dao.MesDao
import java.util.*

class ChatPresenter(private var view: IChatView) : IChatPresenter {

    override fun loadChat(uid: String) {
        val db = RealtimeDB.getInstance()
        val userDao = db.userDao
        val mesDao = db.mesDao

        userDao.getUser(AuthUtils.getCurrentUserUid())
            .addOnSuccessListener {
                val data = it.value as HashMap<String, Object>
                if (data["messages"] != null) {
                    val mesListIds = data["messages"] as HashMap<String, String>
                    for ((key, value) in mesListIds) {
                        if (uid == key) {
                            userDao.getUser(key).addOnSuccessListener { userIt ->
                                val userDetails = userIt.value as HashMap<String, Object>
                                val userToUsername = userDetails["username"] as String
                                val userToProfession = userDetails["profession"] as String
                                view.onUserLoaded(userToUsername, userToProfession)
                                fetchMessages(mesDao, value)
                            }
                        }
                    }
                }
            }
    }

    override fun sendMessage(uid: String, username: String, message: String) {
        RealtimeDB.getInstance().mesDao.addMessage(AuthUtils.getCurrentUserUid(),
        AuthUtils.getCurrentUserUsername(), uid, username, message)
        view.onChatItemLoaded(ChatItem(AuthUtils.getCurrentUserUid(), message, Date()))
    }

    private fun fetchMessages(mesDao: MesDao, mesListId: String) {
        mesDao.getMessages(mesListId).addOnSuccessListener { mesIt ->
            val messages = mesIt.value as HashMap<String, Object>
            var sortedMessages = TreeMap<Long, Object>()
            for ((key1, val1) in messages) {
                sortedMessages[-key1.toLong()] = val1
            }
            for ((key1, val1) in sortedMessages) {
                val messageDetails = val1 as HashMap<String, Object>
                val item = ChatItem(
                    messageDetails["uid"] as String,
                    messageDetails["message"] as String,
                    Date(-key1)
                )
                view.onChatItemLoaded(item)
            }

        }
            .addOnFailureListener {
                return@addOnFailureListener
            }
    }

}