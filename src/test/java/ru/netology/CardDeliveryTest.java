package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    public void openLocalhost() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTest() {
        $("[data-test-id=city] input").setValue("Москва");
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Петушкова Надежда");
        $("[data-test-id=phone] input").setValue("+79220000000");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='notification'] .notification__title")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}