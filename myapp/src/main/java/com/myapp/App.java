package com.myapp;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String defaultMessage = "Hello World! from: ";

        // my message
        System.out.println( defaultMessage + "Hodaya" );

        // properties file
        Properties properties = new Properties();
        File propertiesFile = new File("app.properties");
        if (propertiesFile.exists() && propertiesFile.isFile()) {
            try (FileInputStream fis = new FileInputStream(propertiesFile)) {
                properties.load(fis);
                String propertiesMessage = properties.getProperty("app.message");
                if (propertiesMessage != null && !propertiesMessage.isEmpty()) {
                    System.out.println(defaultMessage + propertiesMessage);
                }
            } catch (IOException e) {
                System.err.println("Could not load properties file: " + e.getMessage());
            }
        }

        // cli exec arguments
        String argsMessage = defaultMessage;
        if (args.length > 0) {
            argsMessage += String.join(" ", args);
            System.out.println(argsMessage);
        }
    }
}
