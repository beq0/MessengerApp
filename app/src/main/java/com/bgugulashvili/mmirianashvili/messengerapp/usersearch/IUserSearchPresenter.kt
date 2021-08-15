package com.bgugulashvili.mmirianashvili.messengerapp.usersearch

interface IUserSearchPresenter {

    fun searchUsers(username: String? = null)

    fun initialize()

}