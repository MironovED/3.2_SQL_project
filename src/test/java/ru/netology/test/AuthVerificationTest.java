package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;


public class AuthVerificationTest {


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanUp() {
        DataHelper.clearDB();
    }


    @Test
    public void loginVerification() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void invalidLoginVerification() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.invalidGetAuthInfo();
        loginPage.inValidLogin(authInfo);
        loginPage.getErrorNotification();
    }


    @Test
    public void enterThePasswordByMistakeThreeTimes() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.invalidGetPassword();
        loginPage.inValidLogin(authInfo);
        loginPage.cleanField();
        loginPage.inValidLogin(authInfo);
        loginPage.cleanField();
        loginPage.inValidLogin(authInfo);
        loginPage.blockNotification();
    }

}


