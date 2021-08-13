package com.bgugulashvili.mmirianashvili.messengerapp.auth.login

interface ILoginPresenter {

    fun onUserCheckAuthentication(username: String, password: String)

}