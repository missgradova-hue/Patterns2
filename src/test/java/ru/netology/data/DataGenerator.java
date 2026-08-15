package ru.netology.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {

    private static final Faker faker =
            new Faker(new Locale("en"));

    private DataGenerator() {
    }

    public static String generateLogin() {
        return faker.name()
                .username()
                .replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String generatePassword() {
        return faker.internet().password();
    }

    public static RegistrationUser generateActiveUser() {
        return new RegistrationUser(
                generateLogin(),
                generatePassword(),
                "active"
        );
    }

    public static RegistrationUser generateBlockedUser() {
        return new RegistrationUser(
                generateLogin(),
                generatePassword(),
                "blocked"
        );
    }
}