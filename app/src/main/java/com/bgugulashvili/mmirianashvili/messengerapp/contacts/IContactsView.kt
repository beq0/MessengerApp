package com.bgugulashvili.mmirianashvili.messengerapp.contacts

interface IContactsView {

    fun onSearch(username: String? = null)

    fun onContactFound(contact: ContactsListItem)
}