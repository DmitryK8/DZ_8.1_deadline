package ru.netology.web.test;

import org.junit.jupiter.api.AfterAll;
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
        loginPage.login(wrongPass);
        loginPage.error();
        loginPage.login(wrongPass);
        loginPage.error();
        loginPage.login(wrongPass);
        loginPage.error();
        String actualStatus = getUserStatus(getAuthInfo().getLogin());
        assertEquals("blocked", actualStatus);
    }

    @AfterAll
    static void cleanDB() {
        deleteFromDB();
    }
}
