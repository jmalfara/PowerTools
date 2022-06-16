package com.jmat.dashboard.ui.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class SplitInstallManagerDelegate(
    val fragment: Fragment,
    val onEvent: (Event) -> Unit
) : ReadOnlyProperty<Fragment, SplitInstallManager> {
    var _value: SplitInstallManager? = null

    private val listener = SplitInstallStateUpdatedListener { state ->
        when (state.status()) {
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                /*
                  This may occur when attempting to download a sufficiently large module.

                  In order to see this, the application has to be uploaded to the Play Store.
                  Then features can be requested until the confirmation path is triggered.
                 */
//                fragment.requireActivity().startIntentSender(
//                    state.resolutionIntent()?.intentSender,
//                    null, 0, 0, 0
//                )
            }
            SplitInstallSessionStatus.CANCELED,
            SplitInstallSessionStatus.CANCELING,
            SplitInstallSessionStatus.FAILED -> onEvent(Event.Failed)
            SplitInstallSessionStatus.DOWNLOADING -> onEvent(Event.Installing)
            SplitInstallSessionStatus.DOWNLOADED -> onEvent(Event.Unknown)
            SplitInstallSessionStatus.INSTALLING -> onEvent(Event.Installing)
            SplitInstallSessionStatus.INSTALLED -> onEvent(Event.Installed)
            SplitInstallSessionStatus.PENDING -> onEvent(Event.Unknown)
            SplitInstallSessionStatus.UNKNOWN -> onEvent(Event.Unknown)
        }
    }

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                _value = SplitInstallManagerFactory.create(fragment.requireActivity()).apply {
                    registerListener(listener)
                }
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                _value?.unregisterListener(listener)
                _value = null
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): SplitInstallManager {
        val value = _value
        if (value != null) {
            return value
        }

        val lifecycle = thisRef.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Binding should never be accessed outside of the lifecycle")
        }

        throw IllegalStateException("SplitInstallManager is null")
    }

    sealed class Event {
        object Installing: Event()
        object Installed: Event()
        object Failed: Event()
        object Unknown: Event()
    }
}

fun Fragment.splitInstallManager(
    onEvent: (SplitInstallManagerDelegate.Event) -> Unit
) = SplitInstallManagerDelegate(this, onEvent)