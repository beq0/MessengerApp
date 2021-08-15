package com.bgugulashvili.mmirianashvili.messengerapp.data.dao

import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserDao(firebaseDatabase: FirebaseDatabase) {

    private var ref: DatabaseReference = firebaseDatabase.getReference("Users")

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
                val data = it.value as HashMap<String, Object>
                for ((key, value) in data) {
                    val currUserVals = value as HashMap<String, Object>
                    result.add(User(key, currUserVals["username"] as String, currUserVals["profession"] as String))
                }
                if (username != null) {
                    result = ArrayList(result.filter { u -> u.username!!.indexOf(username) != -1 })
                }
                if (callback != null) {
                    callback(result)
                }
            }
    }
}