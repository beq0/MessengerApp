package com.bgugulashvili.mmirianashvili.messengerapp.data.entity.mes

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class MesList(
    val id: String? = null,
    val u1Uid: String? = null,
    val u2Uid: String? = null,
    val u1Username: String? = null,
    val u2Username: String? = null,
    val messages: List<Mes>? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "u1Uid" to u1Uid,
            "u2Uid" to u2Uid,
            "u1Username" to u1Username,
            "u2Username" to u2Username,
            "messages" to messages
        )
    }
}