package com.bgugulashvili.mmirianashvili.messengerapp.data.entity.mes

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Mes(
    val uid: String? = null,
    val message: String? = null,
    val messageTime: Long? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "message" to message,
            "messageTime" to messageTime
        )
    }
}