package com.bgugulashvili.mmirianashvili.messengerapp.auth.login

import android.util.Log
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginPresenter(var view: ILoginView) : ILoginPresenter {

    private val interactor = LoginInteractor(this)

    override fun onUserCheckAuthentication(username: String, password: String) {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(AuthUtils.getEmailFromUsername(username), password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view.authenticationSuccessful(auth.currentUser!!)
                } else {
                    Log.e(AuthUtils.LOG_TAG, task.exception.toString())
                    view.authenticationFailed()
                }
            }
    }

}