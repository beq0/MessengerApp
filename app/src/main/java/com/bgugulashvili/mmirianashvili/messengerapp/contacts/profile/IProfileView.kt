package com.bgugulashvili.mmirianashvili.messengerapp.contacts.profile

import com.bgugulashvili.mmirianashvili.messengerapp.data.entity.User

interface IProfileView {

    fun onUpdate(username: String, profession: String)

    fun onUpdateFailed()

    fun onUpdateRequiresAuthentication()

    fun onSignOut()

    fun onProfileFetched(user: User)

    fun onProfileFetchFailed()

}