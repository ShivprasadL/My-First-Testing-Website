package com.website.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties prop;

    public Properties init_prop() {
        prop = new Properties();
        try {
            // Path points to your resources folder
            FileInputStream ip = new FileInputStream("./src/main/resources/config.properties");
            prop.load(ip);
        } catch (IOException e) {
            System.out.println("Error reading config.properties file");
            e.printStackTrace();
        }
        return prop;
    }
}