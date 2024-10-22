package controller;

import model.DatabaseConnection;
import model.Dividend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DividendController {
    public static void distributeDividend(Dividend dividend) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO dividends (member_id, amount) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, dividend.getMemberId());
            stmt.setDouble(2, dividend.getAmount());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
