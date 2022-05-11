package ru.netology.data;

import lombok.SneakyThrows;
import lombok.Value;

import java.sql.DriverManager;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }


    @Value
    public static class VerificationCode {
        private String code;
    }

    @SneakyThrows
    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        var authCodeSQL = "SELECT * FROM auth_codes;";

        var authCode = 0;
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                var countStmt = conn.createStatement();
        ) {

            try (var rs = countStmt.executeQuery(authCodeSQL)) {
                if (rs.next()) {
                    authCode = rs.getInt(1);
                }
            }
        }
        String result = String.valueOf(authCode);
        return new VerificationCode(result);
    }
}