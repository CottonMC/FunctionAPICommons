package io.github.cottonmc.functionapi.documentation;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.RootCommandNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ContentCommandDocumentationGenerator {

    public void generate(CommandDispatcher<Map<String, Object>> dispatcher){
        Map<String, Object> contentRegistrationContext = new HashMap<>();
        RootCommandNode<Map<String, Object>> root = dispatcher.getRoot();
        String[] allUsage = dispatcher.getAllUsage(root, contentRegistrationContext, false);

        try {

            PrintWriter fileWriter = new PrintWriter("./functionapi.txt");
            fileWriter.write("Function api registration command reference: "+System.lineSeparator());
            for (String s : allUsage) {
                fileWriter.append(s).append(System.lineSeparator());
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
