package com.bgugulashvili.mmirianashvili.messengerapp.shared

import android.content.Context
import com.bgugulashvili.mmirianashvili.messengerapp.usersearch.UserSearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BottomNavigationController {

    companion object {
        private lateinit var bottomNavigationView: BottomNavigationView
        private lateinit var bottomFAB: FloatingActionButton

        fun init(context: Context, bottomNavigationView: BottomNavigationView, bottomFAB: FloatingActionButton) {
            this.bottomNavigationView = bottomNavigationView
            this.bottomFAB = bottomFAB
            init(context)
        }

        private fun init(context: Context) {
            bottomNavigationView.background = null
            bottomNavigationView.menu.getItem(1).isEnabled = false

            bottomFAB.setOnClickListener {
                UserSearchActivity.start(context)
            }
        }
    }

}