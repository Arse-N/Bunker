package com.example.bunker.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HtmlFileGenerator {
    public String createHtmlContentForPlayer(String playerName, String playerData) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>" + playerName + "'s Data</title>" +
                "</head>" +
                "<body>" +
                "<h1>Data for " + playerName + "</h1>" +
                "<p>" + playerData + "</p>" +
                "</body>" +
                "</html>";
    }

    public File writeHtmlToFile(String htmlContent, String playerName) {
        File path = new File("path/to/save"); // Specify your path here
        File file = new File(path, playerName + ".html");
        try (FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(htmlContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
