package com.bgugulashvili.mmirianashvili.messengerapp.auth.login

import com.google.firebase.auth.FirebaseUser

interface ILoginView {

    fun authenticationSuccessful(user: FirebaseUser)

    fun authenticationFailed()

}