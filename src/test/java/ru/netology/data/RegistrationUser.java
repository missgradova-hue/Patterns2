package ru.netology.data;

import lombok.Value;

@Value
public class RegistrationUser {
    String login;
    String password;
    String status;
}