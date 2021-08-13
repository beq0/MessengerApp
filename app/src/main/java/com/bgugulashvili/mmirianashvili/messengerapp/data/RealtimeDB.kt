package com.bgugulashvili.mmirianashvili.messengerapp.data

import com.bgugulashvili.mmirianashvili.messengerapp.data.dao.UserDao
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RealtimeDB(firebaseDatabase: FirebaseDatabase) {

    var userDao: UserDao = UserDao(firebaseDatabase)

    companion object {
        private const val DATABASE_URL = "https://messnegerapp-default-rtdb.europe-west1.firebasedatabase.app/"

        @Volatile private var INSTANTCE: RealtimeDB? = null

        @Synchronized fun getInstance(): RealtimeDB {
            if(INSTANTCE == null){
                INSTANTCE = RealtimeDB(Firebase.database(DATABASE_URL))
            }
            return INSTANTCE!!
        }
    }
}