package com.test.pages;

import com.test.domain.PropertiesConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

import static com.test.BaseTest.wait;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SearchTransportPage extends BasePage {
    private static String PAGE_PATH = "/transport/legkovye-avtomobili/dnepr/q-\n" +
            "%D0%BB%D0%B5%D0%B3%D0%BA%D0%BE%D0%B2%D1%8B%D0%B5-\n" +
            "%D0%B0%D0%B2%D1%82%D0%BE%D0%BC%D0%BE%D0%B1%D0%B8%D0%BB%\n" +
            "D0%B8/";

    @FindBy(css = ".clearbox input")
    private WebElement searchString;

    @FindBy(id = "cityField")
    private WebElement city;

    @FindBy(xpath = "//div//span[@class='block overh']")
    private WebElement searchType;

    @FindBy(xpath = "//span[contains(text(),'Марка')]")
    private WebElement dropDownManufacturers;

    @FindBy(xpath = "//span[contains(@data-default-label,'Цена от')]")
    private WebElement priceFrom;

    @FindBy(xpath = "//span[contains(@data-default-label,'Цена до')]")
    private WebElement priceTo;

    @FindBy(xpath = "//span[contains(@data-default-label,'Пробег от')]")
    private WebElement mileageFrom;

    @FindBy(id = "clearQ")
    private WebElement buttonClearSearch;

    @FindBy(xpath = "//span[contains(@data-default-label, 'Коробка')]")
    private WebElement transmission;

    @FindBy(id = "search-text")
    private WebElement searchText;

    public SearchTransportPage(WebDriver driver) {
        super(driver);
    }

    @Step("check that page is opened")
    @Override
    public boolean isOpened() {
        wait.until(ExpectedConditions.urlContains("/transport/legkovye-avtomobili/dnepr/q-"));
        return driver.getCurrentUrl().contains("/transport/legkovye-avtomobili/dnepr/q-");
    }

    @Override
    public String getUrl() {
        return PropertiesConfig.BASE_URL + PAGE_PATH;
    }

    @Step("get text from search field")
    public String getSearchFieldValue() {
        return searchString.getAttribute("value");
    }

    @Step("get text from city field")
    public String getCityFieldValue() {
        return city.getAttribute("value");
    }

    @Step("get text from search type field")
    public String getSearchTypeValue() {
        return searchType.getText();
    }

    @Step("click on dropdown manufacturers")
    public SearchTransportPage clickOnDropDownManufacturers() {
        waitForLoadingEnds();
        dropDownManufacturers.click();
        return this;
    }

    @Step("get all manufacturers from dropdown list")
    public List<String> getAllManufacturers() {
        List<WebElement> elements = driver.findElements(By.xpath("//li[@id='param_subcat']//ul//a"));
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    @Step("send violating symbols to the field 'price from'")
    public SearchTransportPage sendViolatingSymbolsToFieldPriceFrom() {
        waitForLoadingEnds();
        wait.until(elementToBeClickable(priceFrom));
        priceFrom.click();
        driver.findElement(xpathForHiddenInputField("Цена от")).clear();
        priceFrom.click();
        driver.findElement(xpathForHiddenInputField("Цена от")).sendKeys("asd");
        searchText.click();
        return this;
    }

    private By xpathForHiddenInputField(String dataDefaultLabel) {
        return By.xpath("//span[@data-default-label=\"" + dataDefaultLabel + "\"]/../..//input");
    }

    @Step("sendind digits to the field 'price from'")
    public SearchTransportPage sendNonViolatingSybmbolsToFieldPriceFrom(String price) {
        waitForLoadingEnds();
        wait.until(elementToBeClickable(priceFrom));
        clickOn(priceFrom);
        driver.findElement(xpathForHiddenInputField("Цена от")).clear();
        clickOn(priceFrom);
        driver.findElement(xpathForHiddenInputField("Цена от")).sendKeys(price);
        wait.until(invisibilityOf(driver.findElement(xpathForHiddenInputField("Цена от"))));
        return this;
    }

    @Step("sendind digits symbols to the field 'price to'")
    public SearchTransportPage sendNonViolatingSybmbolsToFieldPriceTo(String price) {
        waitForLoadingEnds();
        wait.until(elementToBeClickable(priceTo));
        clickOn(priceTo);
        driver.findElement(xpathForHiddenInputField("Цена до")).clear();
        clickOn(priceTo);
        driver.findElement(xpathForHiddenInputField("Цена до")).sendKeys(price);
        return this;
    }

    public void clickOn(WebElement element) {
        int waitBetween = 500;
        int tries = 0;
        while (true) {
            try {
                element.click();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                if (tries > PropertiesConfig.EXPLICIT_WAIT) {
                    assertThat(e).isNull();
                    break;
                }
                tries += 1;
                try {
                    Thread.sleep(waitBetween);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Step("clear search field")
    public SearchTransportPage clearSearch() {
        buttonClearSearch.click();
        return this;
    }
    @Step("checking that field 'price from' contains {text}")
    public void checkPriceFromContains(String text) {
        waitUntilTextAppear(priceFrom, text);
    }

    @Step("get price from field 'price from'")
    public String getTextFieldPriceFrom() {
        return priceFrom.getText();
    }

    private void waitForLoadingEnds() {
        wait.until(driver -> (Boolean) ((JavascriptExecutor) driver).
                executeScript("return jQuery.active == 0"));
        wait.until(invisibilityOfElementLocated(By.xpath("//div[contains(@class,'listOverlay')]")));
    }

    @Step("click on first option in mileage dropdown list")
    public String pickFirstOptionInMileageFromList() {
        wait.until(elementToBeClickable(mileageFrom));
        mileageFrom.click();
        List<WebElement> elements = driver.findElements(By.xpath("//li[contains(@class,'dynamic')]//a[@data-unit='км']"));
        WebElement mileage;
        if (elements.size() > 0) {
            mileage = elements.get(0);
            mileage.click();
            return mileage.getText();
        } else {
            assertThat(elements.size()).isNotZero();
            return null;
        }
    }
    @Step("checking that mileage field contains {text}")
    public void checkMileageFromContains(String text) {
        waitUntilTextAppear(mileageFrom, "от " + text);
    }

    @Step("getting all prices from ads on page")
    public List<Integer> getAllPricesFromAds() {
        waitForLoadingEnds();
        return driver.findElements(By.xpath("//tr[@class='wrap']//p[@class='price']"))
                .stream()
                .map(element ->
                        element
                                .getText()
                                .replace(" ", "")
                                .replace("грн.", ""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Step("click on transmission dropdown list")
    public SearchTransportPage clickTransmissionTypeDropDown() {
        transmission.click();
        return this;
    }

    @Step("choose mechanical transmission")
    public SearchTransportPage clickMechanicalTransmission() {
        driver.findElement(By.xpath("//span[contains(text(),'Механическая')]")).click();
        return this;
    }

    @Step("getting info about checkbox transmission 'All'")
    public Boolean transmissionTypeAllChecked() {
        WebElement transmissionAll = driver
                .findElement(By.xpath("//*[contains(@for,'f-all-filter_enum_transmission_type_47')]//input"));
        return isAttribtuePresent(transmissionAll, "checked");
    }

    private boolean isAttribtuePresent(WebElement element, String attribute) {
        boolean result = false;
        try {
            String value = element.getAttribute(attribute);
            if (value != null) {
                result = true;
            }
        } catch (Exception ignored) {
        }

        return result;
    }

    private void waitUntilTextAppear(WebElement webElement, String text) {
        wait.until(textToBePresentInElement(webElement, text));
    }


}
