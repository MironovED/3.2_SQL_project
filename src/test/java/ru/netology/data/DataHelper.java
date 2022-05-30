package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;

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


    public static AuthInfo invalidGetAuthInfo() {
        Faker faker = new Faker();
        return new AuthInfo(
                faker.name().fullName(),
                faker.internet().password()
        );
    }

    public static AuthInfo invalidGetPassword() {
        Faker faker = new Faker();
        return new AuthInfo(
                "vasya",
                faker.internet().password()
        );
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @SneakyThrows
    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        var authCodeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";

        var authCode = 0;
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                var countStmt = conn.createStatement();
        ) {

            try (var rs = countStmt.executeQuery(authCodeSQL)) {
                if (rs.next()) {
                    authCode = rs.getInt("code");
                }
            }
        }
        String result = String.valueOf(authCode);
        return new VerificationCode(result);
    }

    @SneakyThrows
    public static void clearDB() {
        var deleteCode = "DELETE FROM auth_codes";
        var deleteTransaction = "DELETE FROM card_transactions";
        var deleteCard = "DELETE FROM cards";
        var deleteUser = "DELETE FROM users";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "user", "pass")
        ) {
            runner.update(conn, deleteCode);
            runner.update(conn, deleteTransaction);
            runner.update(conn, deleteCard);
            runner.update(conn, deleteUser);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}