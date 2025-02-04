package com.myapp;

import java.io.FileInputStream;
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
        try (FileInputStream fis = new FileInputStream("app.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Could not load properties file: " + e.getMessage());
        }
        String propertiesMessage = defaultMessage + properties.getProperty("app.message", "Hello World! from app.properties");
        System.out.println(propertiesMessage);
        
        // cli exec arguments
        String argsMessage = defaultMessage;
        if (args.length > 0) {
            argsMessage += String.join(" ", args);
            System.out.println(argsMessage);
        }
    }
}
