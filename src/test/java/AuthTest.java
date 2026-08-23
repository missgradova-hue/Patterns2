package ru.netology;


import org.junit.jupiter.api.Test;
import ru.netology.data.Api;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationUser;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class AuthTest {



    @Test
    void shouldLoginWithActiveUser() {
        RegistrationUser user = DataGenerator.generateUser("active");

        Api.registerUser(user);


        open("http://localhost:9999");

        $("[data-test-id='login'] input")
                .setValue(user.getLogin());

        $("[data-test-id='password'] input")
                .setValue(user.getPassword());

        $("[data-test-id='action-login']").click();

        webdriver()
                .shouldHave(url("http://localhost:9999/dashboard"));
        $("h2.heading_size_l")
                .shouldBe(visible)
                .shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldNotLoginWithBlockedUser() {
        RegistrationUser user = DataGenerator.generateUser("blocked");

        Api.registerUser(user);


        open("http://localhost:9999");

        $("[data-test-id='login'] input")
                .setValue(user.getLogin());

        $("[data-test-id='password'] input")
                .setValue(user.getPassword());


        $("[data-test-id='action-login']").click();
        $(".notification_status_error")
                .shouldBe(visible)
                .shouldHave(text("Пользователь заблокирован"));

    }

    @Test
    void shouldNotLoginWithWrongPassword() {
        RegistrationUser user = DataGenerator.generateUser("active");

        Api.registerUser(user);

        String wrongPassword = DataGenerator.generatePassword();

        open("http://localhost:9999");

        $("[data-test-id='login'] input")
                .setValue(user.getLogin());

        $("[data-test-id='password'] input")
                .setValue(wrongPassword);

        $("[data-test-id='action-login']").click();


        $(".notification_status_error")
                .shouldBe(visible)
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginWithUnknownUser() {
        RegistrationUser user = DataGenerator.generateUser("active");

        open("http://localhost:9999");

        $("[data-test-id='login'] input")
                .setValue(user.getLogin());

        $("[data-test-id='password'] input")
                .setValue(user.getPassword());

        $("[data-test-id='action-login']").click();

        $(".notification_status_error")
                .shouldBe(visible)
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}

// x = 40 (мин.)
// y = 400 (мин.)