package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CreditGate;

import static com.codeborne.selenide.Selenide.open;

public class CreditTest {
    String validCardNumber = DataHelper.getApprovedCard().getCardNumber();
    String validMonth = DataHelper.getValidMonth();
    String validYear = DataHelper.getYear(1);
    String validOwner = DataHelper.getValidName();
    String validcvccvv = DataHelper.getValidCVCCVV();

    @BeforeEach
    void setup() {
        open("http://localhost:8080");}

    @Test
    void happyPath() {
        var creditgate = new CreditGate();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, validOwner, validcvccvv);
}
    @Test
    void symbolName() {
        String symbolOwner = DataHelper.getInvalidNameSymbols();
        var creditgate = new CreditGate();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, symbolOwner, validcvccvv);
    }
    @Test
    void numberName() {
        String numberOwner = DataHelper.getInvalidNameNumbers();
        var creditgate = new CreditGate();
        creditgate.fillingCredForm(validCardNumber, validMonth, validYear, numberOwner, validcvccvv);
    }
}