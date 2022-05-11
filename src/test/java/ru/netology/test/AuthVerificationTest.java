package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthVerificationTest {


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void loginVerification() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        new DashboardPage();
    }

    @Test
    public void invalidLoginVerification() {
        $("[data-test-id='login'] .input__control").val(DataHelper.invalidGetAuthInfo().getLogin());
        $("[data-test-id='password'] .input__control").val(DataHelper.invalidGetAuthInfo().getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible);
    }


}


