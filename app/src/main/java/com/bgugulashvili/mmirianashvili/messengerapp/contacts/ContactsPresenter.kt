package com.bgugulashvili.mmirianashvili.messengerapp.contacts

import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB
import com.bgugulashvili.mmirianashvili.messengerapp.data.dao.MesDao
import java.util.*
import kotlin.collections.HashMap

class ContactsPresenter(var view: IContactsView) : IContactsPresenter {

    override fun searchUsers(username: String?) {
        val db = RealtimeDB.getInstance()
        val userDao = db.userDao
        val mesDao = db.mesDao
        userDao.getUser(AuthUtils.getCurrentUserUid())
            .addOnSuccessListener {
                val data = it.value as HashMap<String, Object>
                if (data["messages"] != null) {
                    val mesListIds = data["messages"] as HashMap<String, String>
                    for ((key, value) in mesListIds) {
                        userDao.getUser(key).addOnSuccessListener { userIt ->
                            val userDetails = userIt.value as HashMap<String, Object>
                            val userToUsername = userDetails["username"] as String
                            if (username != null) {
                                if (userToUsername.indexOf(username) != -1) {
                                    fetchMessage(mesDao, value, userToUsername, key)
                                }
                            } else {
                                fetchMessage(mesDao, value, userToUsername, key)
                            }

                        }

                    }
                }
            }
    }

    private fun fetchMessage(mesDao: MesDao, mesListId: String, userToUsername: String,
                            userToUid: String) {
        mesDao.getLatestMessage(mesListId).addOnSuccessListener { mesIt ->
            val message = mesIt.value as HashMap<String, Object>
            for ((key1, val1) in message) {
                val messageDetails = val1 as HashMap<String, Object>
                val item = ContactsListItem(
                    messageDetails["uid"] as String,
                    userToUsername,
                    messageDetails["message"] as String,
                    Date(messageDetails["messageTime"] as Long),
                    userToUid
                )
                view.onContactFound(item)
            }

        }
            .addOnFailureListener {
                return@addOnFailureListener
            }
    }

}