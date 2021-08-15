package com.bgugulashvili.mmirianashvili.messengerapp.usersearch

import com.bgugulashvili.mmirianashvili.messengerapp.data.RealtimeDB

class UserSearchPresenter(private var view: IUserSearchView) : IUserSearchPresenter {

    override fun searchUsers(username: String?) {
        RealtimeDB.getInstance().userDao.findUsers(username) { res -> view.onSearch(res) }
    }

    override fun initialize() {
        RealtimeDB.getInstance().userDao.findUsers(callback = { res -> view.onInitial(res)})
    }

}