package com.jmat.powertools.base.delegate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

//https://zhuinden.medium.com/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c
class ViewBindingDelegate<T : ViewBinding>(
    fragment: Fragment,
    private val onBind: ((View) -> T)?
) : ReadWriteProperty<Fragment, T> {

    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerObserver = Observer<LifecycleOwner?> { viewLifecycleOwner ->

                viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        _value = null
                    }
                })
            }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerObserver)
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val value = _value
        if (value != null) {
            return value
        }

        val lifecycle = thisRef.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Binding should never be accessed outside of the lifecycle")
        }

        return onBind?.invoke(thisRef.requireView())?.also {
            _value = it
        } ?: throw IllegalStateException("ViewBinding onBind hasn't been provided")
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

fun <T : ViewBinding> Fragment.viewBinding(bind: ((View) -> T)? = null): ViewBindingDelegate<T> {
    return ViewBindingDelegate(this, bind)
}
