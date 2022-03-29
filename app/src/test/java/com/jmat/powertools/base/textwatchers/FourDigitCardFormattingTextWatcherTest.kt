package com.jmat.powertools.base.textwatchers

import android.text.Editable
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FourDigitCardFormattingTextWatcherTest {

    private lateinit var textWatcher: FourDigitCardFormattingTextWatcher

    @Before
    fun setup() {
        textWatcher = FourDigitCardFormattingTextWatcher()
    }

    @Test
    fun `test with empty text does not replace`() {
        val editable: Editable = mockk()
        every { editable.toString() } returns ""

        textWatcher.afterTextChanged(editable)

        verify(exactly = 0) { editable.replace(any(), any(), any()) }
    }

    @Test
    fun `test with mixed numbers and letters`() {
        val content = "Foo12bar45"
        val expectedCardNumber = "1245"
        val editable: Editable = mockk(relaxed = true)
        every { editable.toString() } returns content
        every { editable.length } returns content.length

        textWatcher.afterTextChanged(editable)

        verify(exactly = 1) {
            editable.replace(
                eq(0),
                eq(content.length),
                eq(expectedCardNumber),
                eq(0),
                eq(expectedCardNumber.length)
            )
        }
    }

    @Test
    fun `test replacing causes next textChange to be skipped`() {
        val content = "Foo12bar34"
        val expectedCardNumber = "1234"
        val editable: Editable = mockk(relaxed = true)
        every { editable.toString() } returns content
        every { editable.length } returns content.length

        textWatcher.afterTextChanged(editable)
        // Second call is skipped because of self change. Reset after returned
        textWatcher.afterTextChanged(editable)
        textWatcher.afterTextChanged(editable)

        verify(exactly = 2) {
            editable.replace(0, content.length, expectedCardNumber, 0, expectedCardNumber.length)
        }
    }

    @Test
    fun `test formatting raw numbers to 4 blocks of 4 digit text`() {
        val content = "123412341234123412341234"
        val expectedCardNumber = "1234-1234-1234-1234"
        val editable: Editable = mockk(relaxed = true)
        every { editable.toString() } returns content
        every { editable.length } returns content.length

        textWatcher.afterTextChanged(editable)

        verify(exactly = 1) {
            editable.replace(0, content.length, expectedCardNumber, 0, expectedCardNumber.length)
        }
    }

    @Test
    fun `test formatting raw numbers to 4 blocks of 4 digit text mixed`() {
        val content = "1234asd1234as123----4123a4123aaa ...41234"
        val expectedCardNumber = "1234-1234-1234-1234"
        val editable: Editable = mockk(relaxed = true)
        every { editable.toString() } returns content
        every { editable.length } returns content.length

        textWatcher.afterTextChanged(editable)

        verify(exactly = 1) {
            editable.replace(0, content.length, expectedCardNumber, 0, expectedCardNumber.length)
        }
    }

    @Test
    fun `test formatting empty non number`() {
        val content = "a"
        val expectedCardNumber = ""
        val editable: Editable = mockk(relaxed = true)
        every { editable.toString() } returns content
        every { editable.length } returns content.length

        textWatcher.afterTextChanged(editable)

        verify(exactly = 1) {
            editable.replace(0, content.length, expectedCardNumber, 0, expectedCardNumber.length)
        }
    }
}