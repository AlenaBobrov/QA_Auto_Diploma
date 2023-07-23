package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditGate;
import ru.netology.page.PaymentGate;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitTest {
    String validCardNumber = DataHelper.getApprovedCard().getCardNumber();
    String validMonth = DataHelper.getValidMonth();
    String validYear = DataHelper.getYear(1);
    String validOwner = DataHelper.getValidName();
    String validcvccvv = DataHelper.getValidCVCCVV();
    String declainedCardNumber = DataHelper.getDeclinedCard().getCardNumber();
    String randomCardNumber = DataHelper.getRandomCardNumber().getCardNumber();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    void setup() {
        open("http://localhost:8080");}

    @AfterAll
    static void cleanBase() {
        SQLHelper.cleanBase();
    }

    @Test
    @DisplayName("Отправка заявки с использованием одобренной карты")
    void happyPath() {
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, validcvccvv);
        paymentgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }
    @Test
    @DisplayName("Отправка заявки с использованием не одобренной карты")
    void declinedCard() {
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(declainedCardNumber, validMonth, validYear, validOwner, validcvccvv);
        paymentgate.notificationErrorIsVisible();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }
    @Test
    @DisplayName("Отправка заявки с использованием рандомной карты")
    void randomCard() {
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(randomCardNumber, validMonth, validYear, validOwner, validcvccvv);
        paymentgate.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Номер карты заполнено буквами")
    void lettersCardNumber() {
        String lettersNumber = DataHelper.getValidName();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(lettersNumber, validMonth, validYear, validOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Номер карты заполнено спецсимволами")
    void symbolsCardNumber() {
        String symbolsNumber = DataHelper.getSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(symbolsNumber, validMonth, validYear, validOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Номер карты заполнено не полностью")
    void notCompletelyCardNumber() {
        String notCompletelyNumber = DataHelper.getFifteenNumbers();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(notCompletelyNumber, validMonth, validYear, validOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Номер карты заполнено излишне")
    void excessiveCardNumber() {
        String excessiveNumber = "4444 4444 4444 44411";
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(excessiveNumber, validMonth, validYear, validOwner, validcvccvv);
        paymentgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Номер карты не заполнено")
    void emptyCardNumber() {
        String emptyCardNumber = DataHelper.getEmptyFieldValue();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(emptyCardNumber, validMonth, validYear, validOwner, validcvccvv);
        paymentgate.validationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено буквами")
    void lettersMonth() {
        String lettersMonth = DataHelper.getValidName();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, lettersMonth, validYear, validOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено спецсимволами")
    void symbolsMonth() {
        String symbolsMonth = DataHelper.getSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, symbolsMonth, validYear, validOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено не полностью")
    void notCompletelyMonth() {
        String notCompletelyMonth = DataHelper.getOneNumber();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, notCompletelyMonth, validYear, validOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено излишне")
    void excessiveMonth() {
        String excessiveMonth = "120";
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, excessiveMonth, validYear, validOwner, validcvccvv);
        paymentgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено заполнено больше 12, но меньше 100")
    void thirteenMonth() {
        String excessiveMonth = "13";
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, excessiveMonth, validYear, validOwner, validcvccvv);
        paymentgate.wrongCardExpirationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено заполнено 00")
    void zeroMonth() {
        String zeroMonth = "00";
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, zeroMonth, validYear, validOwner, validcvccvv);
        paymentgate.wrongCardExpirationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц не заполнено")
    void emptyMonth() {
        String emptyMonth = DataHelper.getEmptyFieldValue();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, emptyMonth, validYear, validOwner, validcvccvv);
        paymentgate.validationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено буквами")
    void lettersYear() {
        String lettersYear = DataHelper.getValidName();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, lettersYear, validOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено спецсимволами")
    void symbolsYear() {
        String symbolsYear = DataHelper.getSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, symbolsYear, validOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено значением в прошлом")
    void lastYear() {
        String lastYear = DataHelper.getLastYear();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, lastYear, validOwner, validcvccvv);
        paymentgate.cardExpiredMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено более 5 лет в будущем")
    void overFiveYear() {
        String overFiveYear = DataHelper.getOverFiveYear();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, overFiveYear, validOwner, validcvccvv);
        paymentgate.wrongCardExpirationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено не полностью")
    void notCompletelyYear() {
        String notCompletelyYear = DataHelper.getOneNumber();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, notCompletelyYear, validOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено излишне")
    void excessiveYear() {
        String excessiveYear = "250";
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, excessiveYear, validOwner, validcvccvv);
        paymentgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год не заполнено")
    void emptyYear() {
        String emptyYear = DataHelper.getEmptyFieldValue();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, emptyYear, validOwner, validcvccvv);
        paymentgate.validationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец заполнено только фамилией")
    void surnameName() {
        String surnameOwner = DataHelper.getSurname();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, surnameOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец заполнено кириллицей")
    void сyrillicName() {
        String сyrillicOwner = DataHelper.getCyrillicName();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, сyrillicOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец заполнено спецсимволами")
    void symbolName() {
        String symbolOwner = DataHelper.getSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, symbolOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец заполнено цифрами")
    void numberName() {
        String numberOwner = DataHelper.getFifteenNumbers();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, numberOwner, validcvccvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец заполнено излишне")
    void excessiveOwner() {
        String excessiveOwner = "ddddddddddddddddddddddddddddd ddddddddddddddddddddddddddddddddddd";
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, excessiveOwner, validcvccvv);
        paymentgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец не заполнено")
    void emptyName() {
        String emptyOwner = DataHelper.getEmptyFieldValue();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, emptyOwner, validcvccvv);
        paymentgate.validationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле CVC/CVV заполнено буквами")
    void letterscCvcCvv() {
        String lettersCvcCvv = DataHelper.getValidName();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, lettersCvcCvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле CVC/CVV заполнено спецсимволами")
    void symbolsCvcCvv() {
        String symbolsCvcCvv = DataHelper.getSymbols();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, symbolsCvcCvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле CVC/CVV заполнено не полностью")
    void notCompletelyCvcCvv() {
        String notCompletelyCvcCvv = DataHelper.getOneNumber();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, notCompletelyCvcCvv);
        paymentgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле CVC/CVV заполнено заполнено излишне")
    void excessiveCvcCvv() {
        String excessiveCvcCvv = DataHelper.getFourNumber();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, excessiveCvcCvv);
        paymentgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле CVC/CVV не заполнено")
    void emptyCvcCvv() {
        String emptyCvcCvv = DataHelper.getEmptyFieldValue();
        var paymentgate = new PaymentGate();
        paymentgate.cleanPayField();
        paymentgate.fillingPayForm(validCardNumber, validMonth, validYear, validOwner, emptyCvcCvv);
        paymentgate.validationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
}
