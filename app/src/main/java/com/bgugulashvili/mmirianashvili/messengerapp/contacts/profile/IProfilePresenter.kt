package com.bgugulashvili.mmirianashvili.messengerapp.contacts.profile

interface IProfilePresenter {

    fun update(newUsername: String, profession: String)

    fun fetchProfile()

}