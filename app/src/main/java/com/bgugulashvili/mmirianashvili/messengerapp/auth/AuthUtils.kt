package com.bgugulashvili.mmirianashvili.messengerapp.auth

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthUtils {
    private const val EMAIL_SUFFIX = "@foo.bar"

    const val LOG_TAG = "Auth"

    fun getEmailFromUsername(username: String): String {
        return username + EMAIL_SUFFIX
    }

    fun getUsernameFromEmail(email: String): String {
        return email.subSequence(0, email.indexOf('@')).toString()
    }

    fun getCurrentUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    fun getCurrentUserUsername(): String {
        return getUsernameFromEmail(getCurrentUser()!!.email!!)
    }

    fun signOutUser() {
        Firebase.auth.signOut()
    }

    fun updateCurrentUserEmail(username: String) {
        getCurrentUser()!!.updateEmail(getEmailFromUsername(username))
    }

    fun getCurrentUserUid(): String {
        return getCurrentUser()!!.uid
    }
}