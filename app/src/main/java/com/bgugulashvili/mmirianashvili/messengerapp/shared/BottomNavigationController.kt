package com.bgugulashvili.mmirianashvili.messengerapp.shared

import android.content.Context
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.bgugulashvili.mmirianashvili.messengerapp.R
import com.bgugulashvili.mmirianashvili.messengerapp.usersearch.UserSearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BottomNavigationController {

    companion object {
        private lateinit var bottomNavigationView: BottomNavigationView
        private lateinit var bottomFAB: FloatingActionButton
        private lateinit var viewPager: ViewPager2

        fun init(context: Context, bottomNavigationView: BottomNavigationView, bottomFAB: FloatingActionButton,
                viewPager: ViewPager2) {
            this.bottomNavigationView = bottomNavigationView
            this.bottomFAB = bottomFAB
            this.viewPager = viewPager
            init()
            initListeners(context)
        }

        private fun init() {
            bottomNavigationView.background = null
            bottomNavigationView.menu.getItem(1).isEnabled = false
        }

        private fun initListeners(context: Context) {
            bottomFAB.setOnClickListener {
                UserSearchActivity.start(context)
            }
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_button_home -> {
                        viewPager.currentItem = 0
                    }
                    R.id.menu_button_settings -> {
                        viewPager.currentItem = 1
                    }
                }
                true
            }
        }
    }

}