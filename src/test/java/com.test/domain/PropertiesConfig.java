package com.test.domain;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesConfig {
    private static final Properties props = getProperties();

    public static String BROWSER = Objects.requireNonNull(props).getProperty("browser");
    public static Integer EXPLICIT_WAIT = Integer.parseInt(Objects.requireNonNull(props).getProperty("explicitWait"));
    public static Integer IMPLICIT_WAIT = Integer.parseInt(Objects.requireNonNull(props).getProperty("implicitWait"));
    public static String BASE_URL = "https://www.olx.ua";


    public static Properties getProperties() {
        try {
            Properties props = new Properties();
            InputStream resourceStream = PropertiesConfig.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            props.load(resourceStream);
            return props;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("there is no properties!");
        return null;
    }
}
