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

}