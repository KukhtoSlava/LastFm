package com.slavakukhto.lastfm.androidApp.helpers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object TimeStampHelper {

    @SuppressLint("SimpleDateFormat")
    fun convertToReadDate(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        return simpleDateFormat.format(Date(time))
    }

    @SuppressLint("SimpleDateFormat")
    fun convertToScrobblesReadDate(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm")
        return simpleDateFormat.format(Date(time))
    }

    @SuppressLint("SimpleDateFormat")
    fun convertToDuration(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("m:ss")
        return simpleDateFormat.format(Date(time))
    }
}
