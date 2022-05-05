package ru.netology.test;

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

    @Test
    public void checkTransferToTheFirstCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

    }


}
