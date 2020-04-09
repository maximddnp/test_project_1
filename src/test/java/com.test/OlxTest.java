package com.test;

import com.test.domain.ResultsExecutionExtension;
import com.test.pages.SearchTransportPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ResultsExecutionExtension.class)
public class OlxTest extends BaseTest {

    SearchTransportPage sTP;

    @BeforeEach
    public void openPage() {
        sTP = new SearchTransportPage(webDriver).open();
    }

    @Test
    @DisplayName("Check all default filters")
    void testDefaultFieldsFilter() {
        assertThat(sTP.getSearchFieldValue()).isEqualTo("легковые автомобили");
        assertThat(sTP.getCityFieldValue()).isEqualTo("Днепр");
        assertThat(sTP.getSearchTypeValue()).isEqualTo("Легковые автомобили");
    }

    @Test
    @DisplayName("Check that manufacturers dropdown contains all needed elements")
    void testDropDownManufacturers() {
        sTP.clickOnDropDownManufacturers();
        List<String> allManufacturers = sTP.getAllManufacturers();
        assertThat(allManufacturers).contains("Acura", "Honda", "ВАЗ");
    }

    @Test
    @DisplayName("Check that price validation doesn't accept symbols")
    void testFieldPriceFromNegativePath() {
        String textFieldPriceFrom = sTP
                .sendViolatingSymbolsToFieldPriceFrom()
                .getTextFieldPriceFrom();
        assertThat(textFieldPriceFrom).contains("Цена от (грн.)");
    }

    @Test
    @DisplayName("Check that price validation accept numbers")
    void testFieldPriceFromPositivePath() {
        sTP.sendNonViolatingSybmbolsToFieldPriceFrom("1000");
        sTP.checkPriceFromContains("от 1000 грн.");
    }

    @Test
    @DisplayName("Check filter by mileage")
    void testMileagePickFromList() {
        String firstMileageOptionFromList = sTP.pickFirstOptionInMileageFromList();
        sTP.checkMileageFromContains(firstMileageOptionFromList);
    }

    @Test
    @DisplayName("Check filter by price from/to and check all ads contains right price")
    void testPricePicker() {
        sTP
                .clearSearch()
                .sendNonViolatingSybmbolsToFieldPriceFrom("10000")
                .sendNonViolatingSybmbolsToFieldPriceTo("70000");
        sTP
                .getAllPricesFromAds()
                .forEach(it -> assertThat(it).isBetween(10000, 70000));
    }

    @Test
    @DisplayName("Check transmission checkbox 'All' unchecked")
    void testCheckbox() {
        sTP
                .clickTransmissionTypeDropDown()
                .clickMechanicalTransmission();
        sTP
                .clickTransmissionTypeDropDown();
        assertThat(sTP.transmissionTypeAllChecked()).isFalse();
    }
}
