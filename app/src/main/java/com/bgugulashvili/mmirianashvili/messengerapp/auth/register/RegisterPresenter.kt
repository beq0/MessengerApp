package com.bgugulashvili.mmirianashvili.messengerapp.auth.register

import android.util.Log
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB
import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterPresenter(var view: IRegisterView) : IRegisterPresenter {

    private val interactor = RegisterInteractor(this)

    override fun onUserRegister(username: String, password: String, profession: String) {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(AuthUtils.getEmailFromUsername(username), password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val createdUser = auth.currentUser!!
                    RealtimeDB.getInstance().userDao.addUser(User(createdUser.uid, username, profession))
                    view.registrationSuccessful(createdUser)
                } else {
                    Log.e(AuthUtils.LOG_TAG, task.exception.toString())
                    view.registrationFailed()
                }
            }
    }

}