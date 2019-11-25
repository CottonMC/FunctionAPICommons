package io.github.cottonmc.functionapi.documentation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Helper to print markdown text. Used for documentation!
 *
 * */
public class MarkdownPrinter {
    private final PrintWriter printWriter;

    private final String separator = System.lineSeparator();

    public MarkdownPrinter(String filename) throws FileNotFoundException {
        printWriter = new PrintWriter(filename);
    }

    public MarkdownPrinter header1(String text){
        addHeader(text,"-");
        return this;
    }

    public MarkdownPrinter header2(String text){
        addHeader(text,"=");
        return this;
    }

    public MarkdownPrinter paragraph(String text){
        printWriter.println(text+separator);
        return this;
    }


    public MarkdownPrinter bold(String text){
        formatText(text,"**");
        return this;
    }

    public MarkdownPrinter italics(String text){
        formatText(text,"*");
        return this;
    }

    public MarkdownPrinter boldItalics(String text){
        formatText(text,"***");
        return this;
    }

    public MarkdownPrinter orderedList(List<String> text){
        int index = 1;
        for (String s : text) {
            printWriter.println(index+". "+text);
        }
        return this;
    }
    public MarkdownPrinter unOrderedList(List<String> text){
        for (String s : text) {
            printWriter.println(" - "+text);
        }
        return this;
    }

    public MarkdownPrinter table(List<List<String>> dataWithHeader){
        List<String> header = dataWithHeader.get(0);
        /*| Syntax | Description |
| ----------- | ----------- |
| Header | Title |
| Paragraph | Text |*/
        printWriter.print("|");
        for (String s : header) {
            printWriter.print(s+"|");
        }
        printWriter.println();
        printWriter.print("|");
        for (String s : header) {
            int length = s.length();
            for (int i = 0; i < length; i++) {
                printWriter.print("-");
            }
            printWriter.print("|");
        }
        printWriter.println();

        for (int i = 1; i <dataWithHeader.size(); i++) {
            List<String> data = dataWithHeader.get(i);
            printWriter.print("|");
            for (String s : data) {
                printWriter.print(s+"|");
            }
            printWriter.println();
        }

        return this;
    }

    private void formatText(String text,String format){
        printWriter.print(format+text+format);
    }

    private void addHeader(String text,String marker){
        int length = text.length();
        StringBuilder header = new StringBuilder(text + System.lineSeparator());

        for (int i = 0; i < length; i++) {
            header.append(marker);
        }

        printWriter.println(header);

    }

    public void finish(){
        printWriter.flush();
    }

}
