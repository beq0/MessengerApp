package com.bgugulashvili.mmirianashvili.messengerapp.chat

interface IChatView {

    fun onChatItemLoaded(item: ChatItem)

    fun onUserLoaded(username: String, profession: String)

}