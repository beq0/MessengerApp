package com.bgugulashvili.mmirianashvili.messengerapp.usersearch

import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.User

interface IUserSearchView {

    fun onInitial(users: ArrayList<User>)

    fun onSearch(users: ArrayList<User>)

}