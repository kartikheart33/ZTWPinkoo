package com.ztb.pinkoo.utils

import android.os.SystemClock
import android.view.MenuItem
import android.view.View

class OneClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

class MenuItemOneClickListener(
    private var defaultInterval: Int = 10000,
    private val onSafeCLick: (MenuItem) -> Unit
) : MenuItem.OnMenuItemClickListener {
    private var lastTimeClicked: Long = 0

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return true
        } else {
            lastTimeClicked = SystemClock.elapsedRealtime()
            onSafeCLick(item)
            return true
        }
    }
}
