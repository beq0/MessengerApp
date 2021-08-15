package com.bgugulashvili.mmirianashvili.messengerapp.data.dao

import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB
import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.user.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserDao(firebaseDatabase: FirebaseDatabase) {

    private var ref: DatabaseReference = firebaseDatabase.getReference(RealtimeDB.USERS_TABLE)

    fun getUser(uid: String): Task<DataSnapshot> {
        return ref.child(uid).get()
    }

    fun addUser(user: User): Task<Void> {
        return ref.child(user.uid!!).setValue(user.toMap())
    }

    fun updateUser(uid: String, user: User): Task<Void> {
        return ref.child(uid).updateChildren(user.toMap())
    }

    fun findUsers(username: String? = null, callback: ((ArrayList<User>) -> Unit)? = null) {
        var result: ArrayList<User> = arrayListOf()
        ref.get()
            .addOnSuccessListener {
                if (callback != null) {
                    val data = it.value as HashMap<String, Object>
                    for ((key, value) in data) {
                        val currUserVals = value as HashMap<String, Object>
                        result.add(User(key, currUserVals["username"] as String, currUserVals["profession"] as String))
                    }
                    if (username != null) {
                        result = ArrayList(result.filter { u -> u.username!!.indexOf(username) != -1 })
                    }
                    result = ArrayList(result.filter { u -> u.username!! != AuthUtils.getCurrentUserUsername() })
                    callback(result)
                }
            }
    }

    fun addMessageTo(toUid: String, mesListId: String) {
        ref.child(AuthUtils.getCurrentUserUid()).child("messages").push().key?.let {
            ref.child(AuthUtils.getCurrentUserUid()).child("messages").child(toUid).setValue(mesListId)
        }
        ref.child(toUid).child("messages").push().key?.let {
            ref.child(toUid).child("messages").child(AuthUtils.getCurrentUserUid()).setValue(mesListId)
        }
    }

    fun fetchMesListId(fromUid: String, toUid: String, callback: ((String) -> Unit)? = null) {
       ref.child(fromUid).child("messages").child(toUid).get()
           .addOnSuccessListener {
               if (callback != null && it.exists()) {
                   val mesListId = it.value as String
                   callback(mesListId)
               }
           }
    }
}