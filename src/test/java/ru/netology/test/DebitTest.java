package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CreditGate;
import ru.netology.page.PaymentGate;

import static com.codeborne.selenide.Selenide.open;

public class DebitTest {
    String validCardNumber = DataHelper.getApprovedCard().getCardNumber();
    String validMonth = DataHelper.getValidMonth();
    String validYear = DataHelper.getYear(1);
    String validOwner = DataHelper.getValidName();
    String validcvccvv = DataHelper.getValidCVCCVV();

    String formatError = "Неверный формат";
    String wrongExpirationDateError = "Неверно указан срок действия карты";
    String cardExpiredError = "Истёк срок действия карты";
    String emptyFieldError = "Поле обязательно для заполнения";

    @BeforeEach
    void setup() {
        open("http://localhost:8080");}

    @Test
    void happyPath() {
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, validcvccvv);
    }
    @Test
    void symbolName() {
        String symbolOwner = DataHelper.getInvalidNameSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, symbolOwner, validcvccvv);
    }
    @Test
    void numberName() {
        String numberOwner = DataHelper.getInvalidNameNumbers();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, numberOwner, validcvccvv);
    }
}
