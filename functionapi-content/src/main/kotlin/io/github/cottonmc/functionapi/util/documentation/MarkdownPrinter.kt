package io.github.cottonmc.functionapi.util.documentation

import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer

/**
 * Helper to print markdown text. Used for documentation!
 *
 */


class MarkdownPrinter(private val writer: Writer) {

    private val separator = System.lineSeparator()


    private fun Writer.print(value: StringBuilder) {
        this.print(value.toString())
    }

    private fun Writer.println() {
        this.print("$separator$separator")
    }

    private fun Writer.println(value: String) {
        this.print(value + separator)
    }

    private fun Writer.print(value: String) {
        this.write(value)
    }

    fun header1(text: String): MarkdownPrinter {
        addHeader(text, "-")
        return this
    }

    fun header2(text: String): MarkdownPrinter {
        addHeader(text, "=")
        return this
    }

    fun paragraph(text: String): MarkdownPrinter {
        writer.write("$separator$text$separator")
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

    fun code(text: String): MarkdownPrinter {
        formatText(text, "`")
        return this
    }

    fun br(): MarkdownPrinter {
        writer.println()
        return this
    }


    fun orderedList(text: List<String>): MarkdownPrinter {
        var index = 1
        for (s in text) {
            val marker = "$index. "
            var space = ""

            for (i in 0..marker.length) {
                space += " "
            }

            var first = true;
            for (split in s.split("\n")) {

                if (first) {
                    writer.println("$marker$s")
                } else {
                    writer.println("$space$s")
                }
                first = false
            }
            index++
        }
        return this
    }

    fun unOrderedList(text: List<String>): MarkdownPrinter {
        for (s in text) {
            val marker = " - "
            var space = ""

            for (i in 0..marker.length) {
                space += " "
            }

            var first = true;
            for (split in s.split("\n")) {

                if (first) {
                    writer.println("$marker$s")
                } else {
                    writer.println("$space$s")
                }
                first = false
            }
        }
        return this
    }

    fun table(dataWithHeader: List<List<String>>): MarkdownPrinter {
        val header = dataWithHeader[0]
        /*  | Syntax    | Description |
            | ----------| ----------- |
            | Header    | Title       |
            | Paragraph | Text        |
            */
        writer.print("|")
        for (s in header) {
            writer.print("$s|")
        }
        writer.println()
        writer.print("|")
        for (s in header) {
            val length = s.length
            for (i in 0 until length) {
                writer.print("-")
            }
            writer.print("|")
        }
        writer.println()
        for (i in 1 until dataWithHeader.size) {
            val data = dataWithHeader[i]
            writer.print("|")
            for (s in data) {
                writer.print("$s|")
            }
            writer.println()
        }
        return this
    }

    private fun formatText(text: String, format: String) {
        writer.print(format + text + format)
    }

    private fun addHeader(text: String, marker: String) {
        val length = text.length
        val header = StringBuilder()
        for (i in 0 until length) {
            header.append(marker)
        }

        br()
        writer.println(text)
        writer.println(header.toString())
    }

    fun finish() {
        writer.flush()
    }
}