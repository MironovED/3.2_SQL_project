package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public SelenideElement login = $("[data-test-id='login'] .input__control");
    public SelenideElement password = $("[data-test-id='password'] .input__control");
    public SelenideElement button = $("[data-test-id='action-login']");

    public SelenideElement errorNotification = $("[data-test-id='error-notification']");


    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        login.val(info.getLogin());
        password.val(info.getPassword());
        button.click();
        return new VerificationPage();
    }

    public void inValidLogin(DataHelper.AuthInfo info) {
        login.val(info.getLogin());
        password.val(info.getPassword());
        button.click();
    }

    public void getErrorNotification() {
        errorNotification.shouldHave(Condition.text("Ошибка"));
    }

    public void blockNotification() {
        errorNotification.shouldHave(Condition.text("Система заблокирована"));
    }

    public void cleanField() {
        login.doubleClick().sendKeys(Keys.BACK_SPACE);
        password.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

}
