package com.slavakukhto.lastfm.androidApp.helpers

import android.app.Activity
import android.view.View
import android.widget.Toast

fun Activity.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeDisabled() {
    isEnabled = false
}

fun View.makeEnabled() {
    isEnabled = true
}
