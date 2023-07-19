package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PaymentGate {
    private SelenideElement heading = $(withText("Оплата по карте"));
    private SelenideElement buyButton = $(withText("Купить"));
    private SelenideElement numberCardField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement ownerField = $x("//form//span[contains(text(), 'Владелец')]/following::span[1]/input");
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement nextButton = $(withText("Продолжить"));

    private SelenideElement okStatusNotification = $("[notification_status_ok]");
    private SelenideElement errorStatusNotification = $("[notification_status_error ]");
    private SelenideElement validatorFieldMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement wrongFormatMes = $(byText("Неверный формат"));
    private SelenideElement cardExpireMes = $(byText("Истёк срок действия карты"));
    private SelenideElement wrongExpirationMes = $(byText("Неверно указан срок действия карты"));
    public PaymentGate() {
        buyButton.click();
        heading
                .shouldBe(visible)
                .shouldHave(text("Оплата по карте"));
    }

    public  void fillingPayForm(String card, String month, String year, String owner, String cvccvv) {
        numberCardField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        cvcField.setValue(cvccvv);
        nextButton.click();
    }
}
