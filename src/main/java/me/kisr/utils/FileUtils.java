package me.kisr.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public enum FileUtils {
    ;

    /*
    fileSetup util
     */

    public static void fileSetup() {
        Path filesPath = Path.of("files");
        Path ticketsPath = Path.of("files/tickets");
        Path ownersPath = Path.of("files/owners");
        Path transcriptsPath = Path.of("files/transcripts");

        try {
            if (Files.exists(filesPath) && Files.exists(ticketsPath) && Files.exists(ownersPath) && Files.exists(transcriptsPath)) {
                System.out.println("Directories complete! Starting up now...");
            } else {
                if (!Files.exists(filesPath)) {
                    System.out.println("\"files\" directory not found. Creating one...");
                    Files.createDirectories(filesPath);
                }
                if (!Files.exists(ticketsPath)) {
                    System.out.println("\"files/tickets\" directory not found. Creating one...");
                    Files.createDirectories(ticketsPath);
                }
                if (!Files.exists(ownersPath)) {
                    System.out.println("\"files/owners\" directory not found. Creating one...");
                    Files.createDirectories(ownersPath);
                }
                if (!Files.exists(transcriptsPath)) {
                    System.out.println("\"files/transcripts\" directory not found. Creating one...");
                    Files.createDirectories(transcriptsPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    readFile util
     */

    public static String readFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
