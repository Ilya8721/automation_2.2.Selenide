import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryApplicationTest {

    private String dateForTests(int days) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate date = LocalDate.now().plusDays(days);
        return date.format(formatter);
    }

    @Test
    void validDataTest() {
        String city = "Новосибирск";
        String date = dateForTests(3);
        String name = "Пупкин Василий";
        String phone = "+79990000000";

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue(city);
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'name'] input").setValue(name);
        $("[data-test-id = 'phone'] input").setValue(phone);
        $("[data-test-id = 'agreement']").click();
        $(".button__text").click();
        $("[data-test-id = 'notification']").shouldBe(appear, Duration.ofSeconds(15));
    }

    @Test
    void validDataCapsLockTest() {
        String city = "НОВОСИБИРСК";
        String date = dateForTests(3);
        String name = "ПУПКИН ВАСИЛИЙ";
        String phone = "+79990000000";

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue(city);
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'name'] input").setValue(name);
        $("[data-test-id = 'phone'] input").setValue(phone);
        $("[data-test-id = 'agreement']").click();
        $(".button__text").click();
        $("[data-test-id = 'notification']").shouldBe(appear, Duration.ofSeconds(15));
    }

    @ParameterizedTest
    @CsvSource({"Электросталь", "Elektrostal", "1276848", "^%$#@!"})
    void invalidCityField(String city) {
        String date = dateForTests(3);
        String name = "Пупкин Василий";
        String phone = "+79990000000";

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue(city);
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'name'] input").setValue(name);
        $("[data-test-id = 'phone'] input").setValue(phone);
        $("[data-test-id = 'agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='city']//span[contains(text(), 'Доставка в выбранный город недоступна')]").should(appear);
    }

    @Test
    void emptyCityField() {
        String city = "";
        String date = dateForTests(3);
        String name = "Пупкин Василий";
        String phone = "+79990000000";

        open("http://localhost:9999/");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'name'] input").setValue(name);
        $("[data-test-id = 'phone'] input").setValue(phone);
        $("[data-test-id = 'agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='city']//span[contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }

    @ParameterizedTest
    @CsvSource({"2", "0", "-1", "-30"})
    void invalidDateField(int additionaldays) {
        String city = "Новосибирск";
        String date = dateForTests(additionaldays);
        String name = "Пупкин Василий";
        String phone = "+79990000000";

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue(city);
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'name'] input").setValue(name);
        $("[data-test-id = 'phone'] input").setValue(phone);
        $("[data-test-id = 'agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='date']//span[contains(text(), 'Заказ на выбранную дату невозможен')]").should(appear);
    }

    @Test
    void EmptyDateField() {
        String city = "Новосибирск";
        String date = dateForTests(3);
        String name = "Пупкин Василий";
        String phone = "+79990000000";

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue(city);
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'name'] input").setValue(name);
        $("[data-test-id = 'phone'] input").setValue(phone);
        $("[data-test-id = 'agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='date']//span[contains(text(), 'Неверно введена дата')]").should(appear);
    }

    @ParameterizedTest
    @CsvSource({"Vasya", "1276848", "^%$#@!"})
    void invalidNameField(String name) {
        String city = "Новосибирск";
        String date = dateForTests(3);
        String phone = "+79990000000";

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue(city);
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'name'] input").setValue(name);
        $("[data-test-id = 'phone'] input").setValue(phone);
        $("[data-test-id = 'agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear);
    }

    @Test
    void emptyNameField() {
        String city = "Новосибирск";
        String date = dateForTests(3);
        String name = "";
        String phone = "+79990000000";

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue(city);
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'phone'] input").setValue(phone);
        $("[data-test-id = 'agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }

    @ParameterizedTest
    @CsvSource({"89990000000", "79990000000", "+799900000000", "+899900000000", "+7abc0000000", "+7999!@#$%^&", "+7(999)000-00-00"})
    void invalidPhoneField(String phone) {
        String city = "Новосибирск";
        String date = dateForTests(3);
        String name = "Пупкин Василий";

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue(city);
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'name'] input").setValue(name);
        $("[data-test-id = 'phone'] input").setValue(phone);
        $("[data-test-id = 'agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[contains(text(), 'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear);
    }

    @Test
    void EmptyPhoneField() {
        String city = "Новосибирск";
        String date = dateForTests(3);
        String name = "Пупкин Василий";
        String phone = "";

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue(city);
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'name'] input").setValue(name);
        $("[data-test-id = 'agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }


    @Test
    void unmarkedCheckbox() {
        String city = "Новосибирск";
        String date = dateForTests(3);
        String name = "Пупкин Василий";
        String phone = "+79990000000";

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue(city);
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'name'] input").setValue(name);
        $("[data-test-id = 'phone'] input").setValue(phone);
        $(".button__text").click();
        $x("//label[@data-test-id='agreement'][contains(@class, 'input_invalid')]").should(appear);
    }
}
