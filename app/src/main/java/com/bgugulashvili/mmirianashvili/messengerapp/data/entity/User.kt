package com.bgugulashvili.mmirianashvili.messengerapp.data.entity

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val username: String? = null, val password: String? = null, val profession: String? = null)