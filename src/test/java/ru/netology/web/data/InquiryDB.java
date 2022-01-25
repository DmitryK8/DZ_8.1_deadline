package ru.netology.web.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class InquiryDB {
    private final static QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static String getVerificationCode(String user) {
        val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
        val getId = "SELECT id FROM users WHERE login = '" + user + "'";
        String id = runner.query(conn, getId, new ScalarHandler<>());
        val code = "SELECT code FROM auth_codes WHERE user_id = '" + id + "' ORDER BY created DESC";
        return runner.query(conn, code, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getUserStatus(String user) {
        val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );

        val getStatus = "SELECT status FROM users WHERE login = '" + user + "'";
        return runner.query(conn, getStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void deleteFromDB() {
        val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
        runner.execute(conn, "DELETE FROM auth_codes");
        runner.execute(conn, "DELETE FROM cards");
        runner.execute(conn, "DELETE FROM card_transactions");
        runner.execute(conn, "DELETE FROM users");
    }
}
