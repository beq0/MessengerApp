package com.bgugulashvili.mmirianashvili.messengerapp.auth.register

import com.google.firebase.auth.FirebaseUser

interface IRegisterView {

    fun registrationSuccessful(user: FirebaseUser)

    fun registrationFailed()

}
