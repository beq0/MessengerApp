package com.bgugulashvili.mmirianashvili.messengerapp.data.dao

import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserDao(firebaseDatabase: FirebaseDatabase) {

    private var ref: DatabaseReference = firebaseDatabase.getReference("Users")

    fun getUser(username: String): Task<DataSnapshot> {
        return ref.child(username).get()
    }

    fun addUser(user: User) {
        if (!user.username.isNullOrEmpty()) {
            ref.child(user.username).setValue(user)
        }
    }

}