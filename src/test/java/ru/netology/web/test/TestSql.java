package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.UserLogin;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.InquiryDB.*;
import static ru.netology.web.data.DataHelper.*;

public class TestSql {
    private DashboardPage dashboardPage;
    private UserLogin loginPage;


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @BeforeAll
    static void setupAllureReports() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(false)
                .savePageSource(true));
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldLogin() {
        var user = getAuthInfo();
        var login = new UserLogin().validLogin(user);
        dashboardPage = login.validVerify(getVerificationCode(user.getLogin()));
    }

    @Test
    void shouldBlockPassword() {
        var wrongPass = new AuthInfo(getAuthInfo().getId(), getAuthInfo().getLogin(), "123qwerty");
        loginPage = new UserLogin();
        loginPage.userLogin(wrongPass);
        loginPage.error();
        loginPage.userLogin(wrongPass);
        loginPage.error();
        loginPage.userLogin(wrongPass);
        loginPage.error();
        String actualStatus = getUserStatus(getAuthInfo().getLogin());
        assertEquals("blocked", actualStatus);
    }

    @AfterAll
    static void cleanDB() {
        deleteFromDB();
    }
}
