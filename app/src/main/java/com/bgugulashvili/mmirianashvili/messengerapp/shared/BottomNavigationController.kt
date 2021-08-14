package com.bgugulashvili.mmirianashvili.messengerapp.shared

import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationController {

    companion object {
        private lateinit var bottomNavigationView: BottomNavigationView

        fun init(bottomNavigationView: BottomNavigationView) {
            this.bottomNavigationView = bottomNavigationView
            init()
        }

        private fun init() {
            bottomNavigationView.background = null
            bottomNavigationView.menu.getItem(1).isEnabled = false
        }
    }

}