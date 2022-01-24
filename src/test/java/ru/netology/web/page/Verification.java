package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;
import static org.openqa.selenium.Keys.*;

public class Verification {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement submitButton = $("[data-test-id=action-verify]");

    public Verification() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(String code) {
        codeField.sendKeys(CONTROL + "a", DELETE);
        codeField.setValue(code);
        submitButton.click();
        return new DashboardPage();
    }

}
