package com.bgugulashvili.mmirianashvili.messengerapp.data.dao

import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB
import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.mes.Mes
import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.mes.MesList
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MesDao(firebaseDatabase: FirebaseDatabase) {

    private var ref: DatabaseReference = firebaseDatabase.getReference(RealtimeDB.MESSAGES_TABLE)
    private var db: FirebaseDatabase = firebaseDatabase

    fun addMessage(fromUid: String, fromUsername: String, toUid: String,
                   toUsername: String, message: String) {
        val currTime = Date().time
        val usersRef = db.getReference(RealtimeDB.USERS_TABLE)
        usersRef.child(fromUid).child("messages").child(toUid).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val mesListId = it.value as String
                    ref.child(mesListId).child("messages").push().key?.let {
                        val newMessage = Mes(fromUid, message, currTime)
                        ref.child(mesListId).child("messages").child(currTime.toString())
                            .setValue(newMessage.toMap())
                    }
                } else {
                    ref.push().key?.let { newMesListId ->
                        val mesList = MesList(newMesListId, fromUid, toUid, fromUsername, toUsername, listOf())
                        ref.child(newMesListId).setValue(mesList.toMap())
                            .addOnSuccessListener {
                                ref.child(newMesListId).child("messages").push().key?.let {
                                    val newMessage = Mes(fromUid, message, currTime)
                                    ref.child(newMesListId).child("messages").child(currTime.toString())
                                        .setValue(newMessage.toMap())
                                }
                                RealtimeDB.getInstance().userDao.addMessageTo(toUid, newMesListId)
                            }
                    }
                }
            }
    }

    fun getLatestMessage(mesListId: String): Task<DataSnapshot> {
        return ref.child(mesListId).child("messages").limitToLast(1).get()
    }

    fun getMessages(mesListId: String): Task<DataSnapshot> {
        return ref.child(mesListId).child("messages").get()
    }

}