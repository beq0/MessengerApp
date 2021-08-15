package com.bgugulashvili.mmirianashvili.messengerapp.contacts

interface IContactsView {

    fun onSearch(username: String)

    fun onContactFound(contact: ContactsListItem)
}