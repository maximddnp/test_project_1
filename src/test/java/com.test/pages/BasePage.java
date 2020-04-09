package com.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public abstract class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public <T extends BasePage> T open() {
        driver.get(getUrl());
        assumeTrue(isOpened(), "Page should open.");
        return (T) this;
    }

    public abstract boolean isOpened();

    public abstract String getUrl();
}
