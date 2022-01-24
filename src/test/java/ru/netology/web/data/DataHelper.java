package ru.netology.web.data;

import lombok.*;

public class DataHelper {
    @Value
    public static class AuthInfo {
        private String id;
        private String login;
        private String password;
    }


    public static AuthInfo getAuthInfo() {
        return new AuthInfo("11a8163d-d956-435e-b61d-e036594540c7", "vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("a4beae7a-ff77-470b-a087-6e147ffaabc7", "petya", "123qwerty");
    }
}
