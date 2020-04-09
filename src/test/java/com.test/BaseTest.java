package com.test;

import com.test.domain.PropertiesConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver webDriver;
    public static WebDriverWait wait;

    @BeforeEach
    public void setupTest() {
        webDriver = setupDriver();
        webDriver.manage().window().setSize(new Dimension(500, 1080));
        webDriver.manage().timeouts().implicitlyWait(PropertiesConfig.IMPLICIT_WAIT, TimeUnit.SECONDS);
        wait = new WebDriverWait(webDriver, PropertiesConfig.EXPLICIT_WAIT);
    }

    @AfterEach
    public void teardown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private WebDriver setupDriver() {
        switch (PropertiesConfig.BROWSER) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }


}
