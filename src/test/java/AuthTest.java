package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.Api;
import ru.netology.data.DataGenerator;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.data.RegistrationUser;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class AuthTest {

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = true;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        Configuration.browserCapabilities = options;
    }

    @Test
    void shouldLoginWithActiveUser() {
        RegistrationUser user = DataGenerator.generateActiveUser();

        Api.registerUser(user);

        open("http://localhost:9999");

        $("[data-test-id='login'] input")
                .setValue(user.getLogin());

        $("[data-test-id='password'] input")
                .setValue(user.getPassword());

        $("[data-test-id='action-login']").click();

        webdriver()
                .shouldHave(url("http://localhost:9999/dashboard"));
    }

    @Test
    void shouldNotLoginWithBlockedUser() {
        RegistrationUser user = DataGenerator.generateBlockedUser();

        Api.registerUser(user);

        open("http://localhost:9999");

        $("[data-test-id='login'] input")
                .setValue(user.getLogin());

        $("[data-test-id='password'] input")
                .setValue(user.getPassword());

        $("[data-test-id='action-login']").click();

        $(".notification_status_error")
                .shouldBe(visible);
    }

    @Test
    void shouldNotLoginWithWrongPassword() {
        RegistrationUser user = DataGenerator.generateActiveUser();

        Api.registerUser(user);

        String wrongPassword = DataGenerator.generatePassword();

        open("http://localhost:9999");

        $("[data-test-id='login'] input")
                .setValue(user.getLogin());

        $("[data-test-id='password'] input")
                .setValue(wrongPassword);

        $("[data-test-id='action-login']").click();

        $(".notification_status_error")
                .shouldBe(visible);
    }

    @Test
    void shouldNotLoginWithUnknownUser() {
        RegistrationUser user = DataGenerator.generateActiveUser();

        // Специально НЕ регистрируем пользователя через API

        open("http://localhost:9999");

        $("[data-test-id='login'] input")
                .setValue(user.getLogin());

        $("[data-test-id='password'] input")
                .setValue(user.getPassword());

        $("[data-test-id='action-login']").click();

        $(".notification_status_error")
                .shouldBe(visible);
    }
}