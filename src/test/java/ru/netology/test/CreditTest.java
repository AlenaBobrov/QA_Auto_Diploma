package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditGate;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {
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
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, validOwner, validcvccvv);
        creditgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
}
    @Test
    @DisplayName("Отправка заявки с использованием не одобренной карты")
    void declinedCard() {
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(declainedCardNumber, validMonth, validYear, validOwner, validcvccvv);
        creditgate.notificationErrorIsVisible();
        assertEquals("DECLINED", SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки с использованием рандомной карты")
    void randomCard() {
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(randomCardNumber, validMonth, validYear, validOwner, validcvccvv);
        creditgate.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Номер карты заполнено буквами")
    void lettersCardNumber() {
        String lettersNumber = DataHelper.getValidName();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(lettersNumber, validMonth, validYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Номер карты заполнено спецсимволами")
    void symbolsCardNumber() {
        String symbolsNumber = DataHelper.getSymbols();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(symbolsNumber, validMonth, validYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Номер карты заполнено не полностью")
    void notCompletelyCardNumber() {
        String notCompletelyNumber = DataHelper.getFifteenNumbers();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(notCompletelyNumber, validMonth, validYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Номер карты заполнено излишне")
    void excessiveCardNumber() {
        String excessiveNumber = "4444 4444 4444 44411";
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(excessiveNumber, validMonth, validYear, validOwner, validcvccvv);
        creditgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Номер карты не заполнено")
    void emptyCardNumber() {
        String emptyCardNumber = DataHelper.getEmptyFieldValue();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(emptyCardNumber, validMonth, validYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено буквами")
    void lettersMonth() {
        String lettersMonth = DataHelper.getValidName();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, lettersMonth, validYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test  //Поле "Месяц" заполнено спецсимволами:
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено спецсимволами")
    void symbolsMonth() {
        String symbolsMonth = DataHelper.getSymbols();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, symbolsMonth, validYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено не полностью")
    void notCompletelyMonth() {
        String notCompletelyMonth = DataHelper.getOneNumber();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, notCompletelyMonth, validYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено излишне")
    void excessiveMonth() {
        String excessiveMonth = "120";
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, excessiveMonth, validYear, validOwner, validcvccvv);
        creditgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено заполнено больше 12, но меньше 100")
    void thirteenMonth() {
        String excessiveMonth = "13";
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, excessiveMonth, validYear, validOwner, validcvccvv);
        creditgate.wrongCardExpirationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц заполнено заполнено 00")
    void zeroMonth() {
        String zeroMonth = "00";
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, zeroMonth, validYear, validOwner, validcvccvv);
        creditgate.wrongCardExpirationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Месяц не заполнено")
    void emptyMonth() {
        String emptyMonth = DataHelper.getEmptyFieldValue();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, emptyMonth, validYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено буквами")
    void lettersYear() {
        String lettersYear = DataHelper.getValidName();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, lettersYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено спецсимволами")
    void symbolsYear() {
        String symbolsYear = DataHelper.getSymbols();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, symbolsYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено значением в прошлом")
    void lastYear() {
        String lastYear = DataHelper.getLastYear();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, lastYear, validOwner, validcvccvv);
        creditgate.cardExpiredMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено более 5 лет в будущем")
    void overFiveYear() {
        String overFiveYear = DataHelper.getOverFiveYear();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, overFiveYear, validOwner, validcvccvv);
        creditgate.wrongCardExpirationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено не полностью")
    void notCompletelyYear() {
        String notCompletelyYear = DataHelper.getOneNumber();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, notCompletelyYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год заполнено излишне")
    void excessiveYear() {
        String excessiveYear = "250";
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, excessiveYear, validOwner, validcvccvv);
        creditgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Год не заполнено")
    void emptyYear() {
        String emptyYear = DataHelper.getEmptyFieldValue();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, emptyYear, validOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец заполнено только фамилией")
    void surnameName() {
        String surnameOwner = DataHelper.getSurname();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, surnameOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец заполнено кириллицей")
    void сyrillicName() {
        String сyrillicOwner = DataHelper.getCyrillicName();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, сyrillicOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец заполнено спецсимволами")
    void symbolName() {
        String symbolOwner = DataHelper.getSymbols();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, symbolOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец заполнено цифрами")
    void numberName() {
        String numberOwner = DataHelper.getFifteenNumbers();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, numberOwner, validcvccvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец заполнено излишне")
    void excessiveOwner() {
        String excessiveOwner = "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd";
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, excessiveOwner, validOwner, validcvccvv);
        creditgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле Владелец не заполнено")
    void emptyName() {
        String emptyOwner = DataHelper.getEmptyFieldValue();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, emptyOwner, validcvccvv);
        creditgate.validationMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле CVC/CVV заполнено буквами")
    void letterscCvcCvv() {
        String lettersCvcCvv = DataHelper.getValidName();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, validOwner, lettersCvcCvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле CVC/CVV заполнено спецсимволами")
    void symbolsCvcCvv() {
        String symbolsCvcCvv = DataHelper.getSymbols();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, validOwner, symbolsCvcCvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле CVC/CVV заполнено не полностью")
    void notCompletelyCvcCvv() {
        String notCompletelyCvcCvv = DataHelper.getOneNumber();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, validOwner, notCompletelyCvcCvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле CVC/CVV заполнено заполнено излишне")
    void excessiveCvcCvv() {
        String excessiveCvcCvv = DataHelper.getFourNumber();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, validOwner, excessiveCvcCvv);
        creditgate.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }
    @Test
    @DisplayName("Отправка заявки, в которой поле CVC/CVV не заполнено")
    void emptyCvcCvv() {
        String emptyCvcCvv = DataHelper.getEmptyFieldValue();
        var creditgate = new CreditGate();
        creditgate.cleanField();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, validOwner, emptyCvcCvv);
        creditgate.wrongFormatMessage();
        assertEquals(null, SQLHelper.getCreditStatus());
    }
}