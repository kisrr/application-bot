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

        try {
            if (!Files.exists(filesPath)) {
                System.out.println("Making file directories");
                Files.createDirectories(filesPath);
                Files.createDirectories(ticketsPath);
                Files.createDirectories(ownersPath);
                System.out.println("Finished making file directories");
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
