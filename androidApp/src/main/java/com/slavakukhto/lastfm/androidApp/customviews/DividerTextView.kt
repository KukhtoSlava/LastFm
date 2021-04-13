package com.slavakukhto.lastfm.androidApp.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.slavakukhto.lastfm.androidApp.databinding.ViewDividerTextBinding

class DividerTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    val textView: TextView
        get() = binding.textDiary

    private val binding: ViewDividerTextBinding =
        ViewDividerTextBinding.inflate(LayoutInflater.from(context), this)
}
