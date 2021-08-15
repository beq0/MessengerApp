package com.bgugulashvili.mmirianashvili.messengerapp.chat

interface IChatPresenter {

    fun loadChat(uid: String)

    fun sendMessage(uid: String, username: String, message: String)

}