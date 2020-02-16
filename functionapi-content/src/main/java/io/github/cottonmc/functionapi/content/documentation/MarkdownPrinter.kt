package io.github.cottonmc.functionapi.content.documentation

import java.io.PrintWriter

/**
 * Helper to print markdown text. Used for documentation!
 *
 */
class MarkdownPrinter(filename: String?) {
    private val printWriter: PrintWriter
    private val separator = System.lineSeparator()
    fun header1(text: String): MarkdownPrinter {
        addHeader(text, "-")
        return this
    }

    fun header2(text: String): MarkdownPrinter {
        addHeader(text, "=")
        return this
    }

    fun paragraph(text: String): MarkdownPrinter {
        printWriter.println(text + separator)
        return this
    }

    fun bold(text: String): MarkdownPrinter {
        formatText(text, "**")
        return this
    }

    fun italics(text: String): MarkdownPrinter {
        formatText(text, "*")
        return this
    }

    fun boldItalics(text: String): MarkdownPrinter {
        formatText(text, "***")
        return this
    }

    fun orderedList(text: List<String?>): MarkdownPrinter {
        val index = 1
        for (s in text) {
            printWriter.println("$index. $text")
        }
        return this
    }

    fun unOrderedList(text: List<String?>): MarkdownPrinter {
        for (s in text) {
            printWriter.println(" - $text")
        }
        return this
    }

    fun table(dataWithHeader: List<List<String>>): MarkdownPrinter {
        val header = dataWithHeader[0]
        /*| Syntax | Description |
| ----------- | ----------- |
| Header | Title |
| Paragraph | Text |*/printWriter.print("|")
        for (s in header) {
            printWriter.print("$s|")
        }
        printWriter.println()
        printWriter.print("|")
        for (s in header) {
            val length = s.length
            for (i in 0 until length) {
                printWriter.print("-")
            }
            printWriter.print("|")
        }
        printWriter.println()
        for (i in 1 until dataWithHeader.size) {
            val data = dataWithHeader[i]
            printWriter.print("|")
            for (s in data) {
                printWriter.print("$s|")
            }
            printWriter.println()
        }
        return this
    }

    private fun formatText(text: String, format: String) {
        printWriter.print(format + text + format)
    }

    private fun addHeader(text: String, marker: String) {
        val length = text.length
        val header = StringBuilder(text + System.lineSeparator())
        for (i in 0 until length) {
            header.append(marker)
        }
        printWriter.println(header)
    }

    fun finish() {
        printWriter.flush()
    }

    init {
        printWriter = PrintWriter(filename)
    }
}