package com.jmat.powertools.base.textfieldformatting.decimal

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.google.common.truth.Truth.assertThat
import com.jmat.powertools.base.textfieldformatting.decimal.formatDecimal
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class DecimalFormattingTextFieldValueExtensionsTest {

    @Test
    fun `test formatDecimal empty`() {
        /* | */
        val textFieldValue = TextFieldValue(
            text = "",
            selection = TextRange(1)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        assertThat(result.text).isEqualTo("")
    }

    @Test
    fun `test formatDecimal decimal only becomes 0 decimal`() {
        /* .| */
        val textFieldValue = TextFieldValue(
            text = ".",
            selection = TextRange(1)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("0.")
            assertThat(selection.start).isEqualTo(2)
            assertThat(selection.end).isEqualTo(2)
        }
    }

    @Test
    fun `test formatDecimal 1 digit`() {
        /* 1| */
        val textFieldValue = TextFieldValue(
            text = "1",
            selection = TextRange(1)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("1")
            assertThat(selection.start).isEqualTo(1)
            assertThat(selection.end).isEqualTo(1)
        }
    }

    @Test
    fun `test formatDecimal 0 is accepted`() {
        /* 0| */
        val textFieldValue = TextFieldValue(
            text = "0",
            selection = TextRange(1)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("0")
            assertThat(selection.start).isEqualTo(1)
            assertThat(selection.end).isEqualTo(1)
        }
    }

    @Test
    fun `test formatDecimal 0 decimal is accepted`() {
        /* 0.| */
        val textFieldValue = TextFieldValue(
            text = "0.",
            selection = TextRange(2)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("0.")
            assertThat(selection.start).isEqualTo(2)
            assertThat(selection.end).isEqualTo(2)
        }
    }

    @Test
    fun `test formatDecimal 00 decimal is formatted to 0`() {
        /* 00| */
        val textFieldValue = TextFieldValue(
            text = "00",
            selection = TextRange(2)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("0")
            assertThat(selection.start).isEqualTo(1)
            assertThat(selection.end).isEqualTo(1)
        }
    }

    @Test
    fun `test formatDecimal decimal 00 is accepted`() {
        /* .00| */
        val textFieldValue = TextFieldValue(
            text = ".00",
            selection = TextRange(3)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo(".00")
            assertThat(selection.start).isEqualTo(3)
            assertThat(selection.end).isEqualTo(3)
        }
    }

    @Test
    fun `test formatDecimal paste groupSeparators and 00 is formatted to 0`() {
        /* ,00| */
        val textFieldValue = TextFieldValue(
            text = ",00",
            selection = TextRange(3)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("0")
            assertThat(selection.start).isEqualTo(1)
            assertThat(selection.end).isEqualTo(1)
        }
    }

    @Test
    fun `test formatDecimal 2 digit`() {
        /* 12| */
        val textFieldValue = TextFieldValue(
            text = "12",
            selection = TextRange(2)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        assertThat(result.text).isEqualTo("12")
    }

    @Test
    fun `test formatDecimal 3 digit`() {
        /* 123| */
        val textFieldValue = TextFieldValue(
            text = "123",
            selection = TextRange(3)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        assertThat(result.text).isEqualTo("123")
    }

    @Test
    fun `test formatDecimal 3 digit decimal at the end`() {
        /* 123.| */
        val textFieldValue = TextFieldValue(
            text = "123.",
            selection = TextRange(4)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("123.")
            assertThat(selection.start).isEqualTo(4)
            assertThat(selection.end).isEqualTo(4)
        }
    }

    @Test
    fun `test formatDecimal 4 digit`() {
        /* 1234| */
        val textFieldValue = TextFieldValue(
            text = "1234",
            selection = TextRange(4)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        assertThat(result.text).isEqualTo("1,234")
    }

    @Test
    fun `test formatDecimal 5 digit`() {
        /* 12345| */
        val textFieldValue = TextFieldValue(
            text = "12345",
            selection = TextRange(5)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        assertThat(result.text).isEqualTo("12,345")
    }

    @Test
    fun `test formatDecimal 5 digit partially grouped`() {
        /* 1,2345| */
        val textFieldValue = TextFieldValue(
            text = "1,2345",
            selection = TextRange(6)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        assertThat(result.text).isEqualTo("12,345")
    }

    @Test
    fun `test formatDecimal 5 digit partially grouped middle insert`() {
        /* 1,5|234 */
        val textFieldValue = TextFieldValue(
            text = "1,5234",
            selection = TextRange(3)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("15,234")
            assertThat(selection.start).isEqualTo(3)
            assertThat(selection.end).isEqualTo(3)
        }
    }

    @Test
    fun `test formatDecimal 6 digit`() {
        /* 123456| */
        val textFieldValue = TextFieldValue(
            text = "123456",
            selection = TextRange(6)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        assertThat(result.text).isEqualTo("123,456")
    }

    @Test
    fun `test formatDecimal 6 digit add decimal in middle`() {
        /* 123,.|456 */
        val textFieldValue = TextFieldValue(
            text = "123,.456",
            selection = TextRange(5)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("123.456")
            assertThat(selection.start).isEqualTo(4)
            assertThat(selection.end).isEqualTo(4)
        }
    }

    @Test
    fun `test formatDecimal 6 digit remove decimal in middle`() {
        /* 111.|222 -> 111,222 */
        val textFieldValue = TextFieldValue(
            text = "111222",
            selection = TextRange(3)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("111,222")
            assertThat(selection.start).isEqualTo(3)
            assertThat(selection.end).isEqualTo(3)
        }
    }

    @Test
    fun `test formatDecimal 7 digit`() {
        /* 1234567| */
        val textFieldValue = TextFieldValue(
            text = "1234567",
            selection = TextRange(7)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        assertThat(result.text).isEqualTo("1,234,567")
    }

    @Test
    fun `test formatDecimal 7 digit removes last digit`() {
        /* 1,234,56| */
        val textFieldValue = TextFieldValue(
            text = "1,234,56",
            selection = TextRange(8)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("123,456")
            assertThat(selection.start).isEqualTo(7)
            assertThat(selection.end).isEqualTo(7)
        }
    }

    @Test
    fun `test formatDecimal 8 digit`() {
        /* 12345678| */
        val textFieldValue = TextFieldValue(
            text = "12345678",
            selection = TextRange(8)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("12,345,678")
            assertThat(selection.start).isEqualTo(10)
            assertThat(selection.end).isEqualTo(10)
        }
    }

    @Test
    fun `test formatDecimal 9 digit`() {
        /* 123456789| */
        val textFieldValue = TextFieldValue(
            text = "123456789",
            selection = TextRange(9)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        assertThat(result.text).isEqualTo("123,456,789")
    }

    @Test
    fun `test formatDecimal 9 digit partially grouped middle insert`() {
        /* 12,349|5,678 */
        val textFieldValue = TextFieldValue(
            text = "12,3495,678",
            selection = TextRange(6)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("123,495,678")
            assertThat(selection.start).isEqualTo(6)
            assertThat(selection.end).isEqualTo(6)
        }
    }

    @Test
    fun `test formatDecimal 9 digit removes last digit`() {
        /* 123,456,78| */
        val textFieldValue = TextFieldValue(
            text = "123,456,78",
            selection = TextRange(10)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("12,345,678")
            assertThat(selection.start).isEqualTo(10)
            assertThat(selection.end).isEqualTo(10)
        }
    }

    @Test
    fun `test formatDecimal 9 digit add decimal to the end`() {
        /* 123,456,789.| */
        val textFieldValue = TextFieldValue(
            text = "123,456,789.",
            selection = TextRange(12)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("123,456,789.")
            assertThat(selection.start).isEqualTo(12)
            assertThat(selection.end).isEqualTo(12)
        }
    }

    @Test
    fun `test formatDecimal 9 digit add decimal in middle`() {
        /* 123,45.|6,789 */
        val textFieldValue = TextFieldValue(
            text = "123,45.6,789",
            selection = TextRange(7)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("12,345.6789")
            assertThat(selection.start).isEqualTo(7)
            assertThat(selection.end).isEqualTo(7)
        }
    }

    @Test
    fun `test formatDecimal very large number `() {
        /* 12,345,678,901,234,567,890.123456789| */
        val textFieldValue = TextFieldValue(
            text = "12,345,678,901,234,567,890.123456789",
            selection = TextRange(36)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("12,345,678,901,234,567,890.123456789")
            assertThat(selection.start).isEqualTo(36)
            assertThat(selection.end).isEqualTo(36)
        }
    }

    @Test
    fun `test formatDecimal very large number plus 6`() {
        /* 12,345,678,901,234,567,890.1234567896| */
        val textFieldValue = TextFieldValue(
            text = "12,345,678,901,234,567,890.1234567896",
            selection = TextRange(37)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("12,345,678,901,234,567,890.1234567896")
            assertThat(selection.start).isEqualTo(37)
            assertThat(selection.end).isEqualTo(37)
        }
    }

    @Test
    fun `test formatDecimal ignores non number added to middle`() {
        /* 1,234,5e|67,890.1234 */
        val textFieldValue = TextFieldValue(
            text = "1,234,5e67,890.1234",
            selection = TextRange(8)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("1,234,567,890.1234")
            assertThat(selection.start).isEqualTo(7)
            assertThat(selection.end).isEqualTo(7)
        }
    }

    @Test
    fun `test formatDecimal ignores extra groupSeparator added after the decimal point`() {
        /* 1,234,567,890.12,|34 */
        val textFieldValue = TextFieldValue(
            text = "1,234,567,890.12,34",
            selection = TextRange(17)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("1,234,567,890.1234")
            assertThat(selection.start).isEqualTo(16)
            assertThat(selection.end).isEqualTo(16)
        }
    }

    @Test
    fun `test formatDecimal added leading zero is removed and cursor moved accordingly`() {
        /* 0|1,234,567,890.1234 */
        val textFieldValue = TextFieldValue(
            text = "01,234,567,890.1234",
            selection = TextRange(1)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("1,234,567,890.1234")
            assertThat(selection.start).isEqualTo(0)
            assertThat(selection.end).isEqualTo(0)
        }
    }

    @Test
    fun `test formatDecimal many leading zeros is removed and cursor moved accordingly`() {
        /* 0001,234,567,890.1234| */
        val textFieldValue = TextFieldValue(
            text = "0001,234,567,890.1234",
            selection = TextRange(21)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("1,234,567,890.1234")
            assertThat(selection.start).isEqualTo(18)
            assertThat(selection.end).isEqualTo(18)
        }
    }

    @Test
    fun `test formatDecimal move decimal behind`() {
        /* 1,234,567,890.12.|34 */
        val textFieldValue = TextFieldValue(
            text = "1,234,567,890.12.34",
            selection = TextRange(17)
        )

        val result = textFieldValue.formatDecimal(Locale.CANADA)

        with(result) {
            assertThat(text).isEqualTo("123,456,789,012.34")
            assertThat(selection.start).isEqualTo(16)
            assertThat(selection.end).isEqualTo(16)
        }
    }

    @Test
    fun `test formatDecimal ignores extra groupSeparator added after the decimal point FRENCH`() {
        /* 1 234 567 890,12 |34 */
        val textFieldValue = TextFieldValue(
            text = "1 234 567 890,12 34",
            selection = TextRange(17)
        )

        val result = textFieldValue.formatDecimal(Locale.FRENCH)

        with(result) {
            assertThat(text).isEqualTo("1 234 567 890,1234")
            assertThat(selection.start).isEqualTo(16)
            assertThat(selection.end).isEqualTo(16)
        }
    }

    @Test
    fun `test formatDecimal ignores period symbol after decimal FRENCH`() {
        /* 1 234 567 890,12.|34 */
        val textFieldValue = TextFieldValue(
            text = "1 234 567 890,12.34",
            selection = TextRange(17)
        )

        val result = textFieldValue.formatDecimal(Locale.FRENCH)

        with(result) {
            assertThat(text).isEqualTo("1 234 567 890,1234")
            assertThat(selection.start).isEqualTo(16)
            assertThat(selection.end).isEqualTo(16)
        }
    }
}