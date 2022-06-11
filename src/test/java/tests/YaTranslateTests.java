package tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class YaTranslateTests extends TestBase{

    @ValueSource(strings = {"Пацаны", "Офис"})
    @ParameterizedTest(name = "При вводе в поле поиска фильма {0} в результатах на первом месте отображается {0}")
    void checkResultOnFirstPosition(String testData) {
        Selenide.open("https://www.kinopoisk.ru/");
        $("[name=kp_query]").setValue(testData);
        $("[data-index='0']").shouldHave(text(testData));
    }

    @CsvSource(value = {
            "gfwfys, Пацаны",
            "jabc, Офис"
    })
    @ParameterizedTest(name = "пользователь случайно ввел название фильма {0} англиской раскладкой {1}")
    void checkResultWithIncorrectInput(String incorrectName, String expectedResult) {
        Selenide.open("https://www.kinopoisk.ru/");
        $("[name=kp_query]").setValue(incorrectName);
        $("[data-index='0']").shouldHave(text(expectedResult));
    }
}
