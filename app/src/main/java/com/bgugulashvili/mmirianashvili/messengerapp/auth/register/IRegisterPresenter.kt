package com.bgugulashvili.mmirianashvili.messengerapp.auth.register

interface IRegisterPresenter {

    fun onUserRegister(username: String, password: String, profession: String)

}
