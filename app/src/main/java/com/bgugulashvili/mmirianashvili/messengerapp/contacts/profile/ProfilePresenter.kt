package com.bgugulashvili.mmirianashvili.messengerapp.contacts.profile

import android.util.Log
import com.bgugulashvili.mmirianashvili.messengerapp.auth.AuthUtils
import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB
import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.User
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

class ProfilePresenter(private var view: IProfileView) : IProfilePresenter {

    override fun update(newUsername: String, profession: String) {
        val currentUser = AuthUtils.getCurrentUser()!!
        RealtimeDB.getInstance().userDao.updateUser(
            currentUser.uid,
            User(currentUser.uid, newUsername, profession)
        )
            .addOnSuccessListener {
                AuthUtils.getCurrentUser()!!.updateEmail(AuthUtils.getEmailFromUsername(newUsername))
                    .addOnSuccessListener {
                        view.onUpdate(newUsername, profession)
                    }
                    .addOnFailureListener {
                        if (it is FirebaseAuthRecentLoginRequiredException) {

                        } else {
                            updateFailed(currentUser, it)
                        }
                    }
            }
            .addOnFailureListener {
                updateFailed(currentUser, it)
            }
    }

    private fun updateFailed(currentUser: FirebaseUser, it: Exception) {
        Log.e("Profile", "Could not update User ${AuthUtils.getUsernameFromEmail(currentUser.email!!)}", it)
        view.onUpdateFailed()
    }

    override fun fetchProfile() {
        val currentUser = AuthUtils.getCurrentUser()!!
        RealtimeDB.getInstance().userDao.getUser(currentUser.uid)
            .addOnSuccessListener {
                val result = it.value as HashMap<String, Object>
                view.onProfileFetched(
                    User(currentUser.uid, result["username"] as String, result["profession"] as String)
                )
            }
            .addOnFailureListener {
                Log.e("Profile", "Could not fetch User ${AuthUtils.getUsernameFromEmail(currentUser.email!!)}", it)
                view.onProfileFetchFailed()
            }
    }

}