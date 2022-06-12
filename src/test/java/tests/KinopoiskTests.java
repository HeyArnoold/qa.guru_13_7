package tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class KinopoiskTests extends TestBase{

    @ValueSource(strings = {"Пацаны", "Офис"})
    @ParameterizedTest(name = "При вводе в поле поиска фильма {0} в результатах на первом месте отображается {0}")
    void checkResultOnFirstPosition(String testData) {
        Selenide.open("https://www.kinopoisk.ru/");
        $("[name=kp_query]").setValue(testData);
        $("[data-index='0']").shouldHave(text(testData));
    }

    @EnumSource(Films.class)
    @ParameterizedTest(name = "При вводе в поле поиска фильма {0} в результатах на первом месте отображается {0}")
    void checkAnyFilmsInTopWithEnum(Films film) {
        Selenide.open("https://www.kinopoisk.ru/");
        $("[name=kp_query]").setValue(film.film);
        $("[data-index='0']").shouldHave(text(film.film));
    }

    @CsvSource(value = {
            "gfwfys, Пацаны",
            "jabc, Офис"
    })
    @ParameterizedTest(name = "пользователь случайно ввел название фильма {1} англиской раскладкой {0}")
    void checkResultWithIncorrectInput(String incorrectName, String expectedResult) {
        Selenide.open("https://www.kinopoisk.ru/");
        $("[name=kp_query]").setValue(incorrectName);
        $("[data-index='0']").shouldHave(text(expectedResult));
    }

    @MethodSource()
    @ParameterizedTest(name = "Проверяем, что фильмы {0} входят в первые 50 лучших фильмов")
    void checkAnyFilmsInTop(List<String> films) {
        Selenide.open("https://www.kinopoisk.ru/lists/movies/top250");
        $$("[class='styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj']")
                .shouldHave(CollectionCondition.containExactTextsCaseSensitive(films));
    }

    private static Stream<Arguments> checkAnyFilmsInTop() {

        return Stream.of(
                Arguments.of(List.of("Зеленая миля", "Список Шиндлера", "Побег из Шоушенка"))
        );
    }
}
