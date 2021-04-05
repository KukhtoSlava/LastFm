package com.slavakukhto.lastfm.androidApp.customviews

import android.content.Context
import android.graphics.ColorFilter
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.textfield.TextInputLayout
import com.slavakukhto.lastfm.androidApp.R
import java.lang.reflect.Field

class NoChangingBackgroundTextInputLayout : TextInputLayout {

    private val backgroundDefaultColorFilter: ColorFilter?
        @Nullable
        get() {
            var defaultColorFilter: ColorFilter? = null
            editText?.let {
                if (it.background != null) {
                    defaultColorFilter = DrawableCompat.getColorFilter(it.background)
                }
            }
            return defaultColorFilter
        }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        val view = View(context)
        view.layoutParams = LinearLayout.LayoutParams(0, 20)
        addView(view)
        isCounterEnabled = true
    }

    override fun setError(@Nullable error: CharSequence?) {
        val defaultColorFilter = backgroundDefaultColorFilter
        super.setError(error)
        updateBackgroundColorFilter(defaultColorFilter)
        val field: Field? = TextInputLayout::class.java.getDeclaredField("counterView")
        field?.let {
            it.isAccessible = true
            val counterView: TextView = field.get(this) as TextView
            counterView.text = ""
            counterView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_field, 0)
            if (error == null) {
                counterView.visibility = View.GONE
            } else {
                counterView.visibility = View.VISIBLE
            }
        }
    }

    override fun drawableStateChanged() {
        val defaultColorFilter = backgroundDefaultColorFilter
        super.drawableStateChanged()
        updateBackgroundColorFilter(defaultColorFilter)
    }

    private fun updateBackgroundColorFilter(colorFilter: ColorFilter?) {
        editText?.let {
            if (it.background != null) {
                it.background.colorFilter = colorFilter
            }
        }
    }
}
