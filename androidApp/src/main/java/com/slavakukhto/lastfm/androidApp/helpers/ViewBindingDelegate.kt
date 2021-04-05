package com.slavakukhto.lastfm.androidApp.helpers

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Extension function for [FragmentActivity] to safely create [ViewBinding]
 * implementation for activities.
 *
 * Usage:
 * private val binding by viewBinding { ActivityBinding.bind(it) }
 *
 * @param init lambda function for initialize [ViewBinding]
 */
fun <T : ViewBinding> FragmentActivity.viewBinding(init: (View) -> T): Lazy<T> = object : Lazy<T> {

    private var binding: T? = null

    override val value: T
        get() = binding ?: init(getContentView()).also {
            binding = it
        }

    override fun isInitialized(): Boolean = binding != null

    private fun FragmentActivity.getContentView(): View {
        return checkNotNull(findViewById<ViewGroup>(android.R.id.content).getChildAt(0)) {
            "Call setContentView or use Activity's secondary constructor passing layout res id."
        }
    }
}

/**
 * Extension function for [Fragment] to safely create [ViewBinding] implementation for fragments.
 *
 * Usage:
 * private val binding by viewBinding { FragmentBinding.bind(it) }
 *
 * @param init lambda function for initialize [ViewBinding]
 */
fun <T : ViewBinding> Fragment.viewBinding(init: (View) -> T): ReadOnlyProperty<Fragment, T> {
    return object : ReadOnlyProperty<Fragment, T> {

        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            (requireView().getTag(property.name.hashCode()) as? T)?.let { return it }
            return init(requireView()).also {
                requireView().setTag(property.name.hashCode(), it)
            }
        }
    }
}
