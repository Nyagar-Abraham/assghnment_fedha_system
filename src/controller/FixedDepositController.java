package controller;

import model.DatabaseConnection;
import model.FixedDeposit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FixedDepositController {
    public static void createFixedDeposit(FixedDeposit deposit) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO fixed_deposits (member_id, deposit_amount, interest_rate) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, deposit.getMemberId());
            stmt.setDouble(2, deposit.getDepositAmount());
            stmt.setDouble(3, deposit.getInterestRate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
