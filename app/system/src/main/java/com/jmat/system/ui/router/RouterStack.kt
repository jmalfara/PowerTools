package com.jmat.system.ui.router

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RouterStack(
    initial: String,
    val onStackEmpty: () -> Unit,
    private val coroutineScope: CoroutineScope
) {
    private val backStack = mutableListOf(initial)
    private val _currentRoute = MutableStateFlow(initial)
    val currentRoute: StateFlow<String> = _currentRoute

    fun push(route: String) {
        backStack.add(route)
        coroutineScope.launch {
            _currentRoute.emit(route)
        }
    }

    fun pop() {
        if (backStack.size == 1) {
            onStackEmpty()
            return
        }
        backStack.removeLast()
        val currentRoute = backStack.last()

        coroutineScope.launch {
            _currentRoute.emit(currentRoute)
        }
    }
}

@Composable
fun rememberNavigationStack(
    initial: String,
    onStackEmpty: () -> Unit
): RouterStack {
    val coroutineScope = rememberCoroutineScope()
    val stack = remember {
        RouterStack(initial, onStackEmpty, coroutineScope)
    }
    BackHandler(true, stack::pop)
    return stack
}