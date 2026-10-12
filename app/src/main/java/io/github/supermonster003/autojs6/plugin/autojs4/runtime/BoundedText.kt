package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import java.nio.charset.StandardCharsets

/** UTF-8 aware truncation helpers; all protocol strings are bounded in bytes, not characters. */
internal object BoundedText {

    fun truncate(text: String, maxBytes: Int): String {
        if (text.toByteArray(StandardCharsets.UTF_8).size <= maxBytes) return text
        var low = 0
        var high = text.length
        while (low < high) {
            val middle = (low + high + 1) ushr 1
            if (text.substring(0, middle).toByteArray(StandardCharsets.UTF_8).size <= maxBytes) {
                low = middle
            } else {
                high = middle - 1
            }
        }
        if (low > 0 && low < text.length && text[low - 1].isHighSurrogate() && text[low].isLowSurrogate()) {
            low--
        }
        return text.substring(0, low)
    }

    /** Splits [text] into pieces whose UTF-8 size does not exceed [maxBytes]; never splits a surrogate pair. */
    fun chunk(text: String, maxBytes: Int): List<String> {
        require(maxBytes >= 4) { "maxBytes must hold at least one code point" }
        if (text.toByteArray(StandardCharsets.UTF_8).size <= maxBytes) return listOf(text)
        val chunks = ArrayList<String>()
        var remaining = text
        while (remaining.isNotEmpty()) {
            val head = truncate(remaining, maxBytes)
            if (head.isEmpty()) {
                // A single code point larger than maxBytes cannot happen for maxBytes >= 4.
                break
            }
            chunks.add(head)
            remaining = remaining.substring(head.length)
        }
        return chunks
    }
}
