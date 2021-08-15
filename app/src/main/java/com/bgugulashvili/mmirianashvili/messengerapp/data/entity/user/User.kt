package com.bgugulashvili.mmirianashvili.messengerapp.data.entity.user

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val uid: String? = null,
    val username: String? = null,
    val profession: String? = null,
    val messages: List<String>? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "username" to username,
            "profession" to profession
        )
    }
}