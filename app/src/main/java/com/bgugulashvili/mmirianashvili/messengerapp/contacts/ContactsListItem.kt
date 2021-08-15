package com.bgugulashvili.mmirianashvili.messengerapp.contacts

import java.util.*

data class ContactsListItem(var uid: String, var name: String, var lastMessage: String,
                            var lastMessageTime: Date, var toUid: String)