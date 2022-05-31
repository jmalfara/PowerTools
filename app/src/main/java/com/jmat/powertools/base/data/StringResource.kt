package com.jmat.powertools.base.data

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

data class TextResource(
    @StringRes val id: Int,
    private val arguments: List<Any> = listOf()
) {
    internal val Resources.value: CharSequence get() = uiText()

    private fun Resources.uiText(): CharSequence {
        val arguments = parseArguments(arguments)
        return if (id == 0) {
            arguments.foldRight("") { charSequence, acc ->
                "$acc$charSequence"
            }
        } else {
            getString(id, *arguments)
        }
    }

    companion object {
        val Empty = TextResource(0)

        operator fun invoke(text: CharSequence) = TextResource(0, listOf(text))
        operator fun invoke(
            @StringRes id: Int,
            vararg arguments: List<Any>
        ) = TextResource(id, arguments.toList())
    }
}

private fun Resources.parseArguments(
    arguments: List<Any>
): Array<out CharSequence> {
    return arguments.map { arg ->
        when (arg) {
            is TextResource -> with(arg) { value }
            else -> arg.toString()
        }
    }.toTypedArray()
}

fun Resources.getText(text: TextResource): CharSequence = with(text) { value }
fun Fragment.getText(text: TextResource): CharSequence = resources.getText(text)