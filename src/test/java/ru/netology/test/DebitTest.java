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
    @Test  //Поле "Номер карты" заполнено буквами
    void lettersCardNumber() {
        String lettersNumber = DataHelper.getValidName();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(lettersNumber, validMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Номер карты" заполнено спецсимволами:
    void symbolsCardNumber() {
        String symbolsNumber = DataHelper.getSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(symbolsNumber, validMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Номер карты" заполнено не полностью:
    void notCompletelyCardNumber() {
        String notCompletelyNumber = DataHelper.getFifteenNumbers();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(notCompletelyNumber, validMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Номер карты" заполнено излишне:
    void excessiveCardNumber() {
        String excessiveNumber = DataHelper.getSeventeenNumbers();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(excessiveNumber, validMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Номер карты" не заполнено
    void emptyCardNumber() {
        String emptyCardNumber = DataHelper.getEmptyFieldValue();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(emptyCardNumber, validMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Месяц" заполнено буквами
    void lettersMonth() {
        String lettersMonth = DataHelper.getValidName();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, lettersMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Месяц" заполнено спецсимволами:
    void symbolsMonth() {
        String symbolsMonth = DataHelper.getSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, symbolsMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Месяц" заполнен не полностью:
    void notCompletelyMonth() {
        String notCompletelyMonth = DataHelper.getOneNumber();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, notCompletelyMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Месяц" заполнено излишне:
    void excessiveMonth() {
        String excessiveMonth = DataHelper.getThreeNumber();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, excessiveMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Месяц" заполнено больше 12, но меньше 100:
    void thirteenMonth() {
        String excessiveMonth = 13;
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, excessiveMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Месяц" заполнено 00:
    void zeroMonth() {
        String zeroMonth = 00;
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, zeroMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Месяц" не заполнено
    void emptyMonth() {
        String emptyMonth = DataHelper.getEmptyFieldValue();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, emptyMonth, validYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Год" заполнено буквами
    void lettersYear() {
        String lettersYear = DataHelper.getValidName();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, lettersYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Год" заполнено спецсимволами:
    void symbolsYear() {
        String symbolsYear = DataHelper.getSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, symbolsYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Год" заполнено значением в прошлом:
    void lastYear() {
        String lastYear = DataHelper.getLastYear();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, lastYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Год" заполнено более 5 лет в будущем:
    void overFiveYear() {
        String overFiveYear = DataHelper.getOverFiveYear();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, overFiveYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Год" заполнено не полностью:
    void notCompletelyYear() {
        String notCompletelyYear = DataHelper.getOneNumber();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, notCompletelyYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Год" не заполнено
    void emptyYear() {
        String emptyYear = DataHelper.getEmptyFieldValue();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, emptyYear, validOwner, validcvccvv);
    }
    @Test  //Поле "Владелец" заполнено кириллицей
    void сyrillicName() {
        String сyrillicOwner = DataHelper.getCyrillicName();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, сyrillicOwner, validcvccvv);
    }
    @Test //символы вместо имени
    void symbolName() {
        String symbolOwner = DataHelper.getSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, symbolOwner, validcvccvv);
    }
    @Test //цифры вместо имени
    void numberName() {
        String numberOwner = DataHelper.getFifteenNumbers();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, numberOwner, validcvccvv);
    }
    @Test  //пустое поле вместо имени
    void emptyName() {
        String emptyOwner = DataHelper.getEmptyFieldValue();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, emptyOwner, validcvccvv);
    }
    @Test  //Поле "CVC/CVV" заполнено буквами
    void letterscCvcCvv() {
        String lettersCvcCvv = DataHelper.getValidName();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, lettersCvcCvv);
    }
    @Test  //Поле "CVC/CVV" заполнено спецсимволами:
    void symbolsCvcCvv() {
        String symbolsCvcCvv = DataHelper.getSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, symbolsCvcCvv);
    }
    @Test  //Поле "CVC/CVV" заполнено не полностью:
    void notCompletelyCvcCvv() {
        String notCompletelyCvcCvv = DataHelper.getOneNumber();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, notCompletelyCvcCvv);
    }
    @Test  //Поле "CVC/CVV" заполнено излишне:
    void excessiveCvcCvv() {
        String excessiveCvcCvv = DataHelper.getFourNumber();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, excessiveCvcCvv);
    }
    @Test  //Поле "CVC/CVV" не заполнено
    void emptyCvcCvv() {
        String emptyCvcCvv = DataHelper.getEmptyFieldValue();
        var paymentgate = new PaymentGate();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, emptyCvcCvv);
    }
}
