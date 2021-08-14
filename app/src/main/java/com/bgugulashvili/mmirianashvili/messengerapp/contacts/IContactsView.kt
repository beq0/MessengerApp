package com.bgugulashvili.mmirianashvili.messengerapp.contacts

import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.User

interface IContactsView {

    fun onSearch(users: List<User>)

}