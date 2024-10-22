package controller;

import model.DatabaseConnection;
import model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoanController {
    public static void applyForLoan(Loan loan) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO loans (member_id, loan_type, amount, interest_rate, repayment_period, monthly_repayment) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, loan.getMemberId());
            stmt.setString(2, loan.getLoanType());
            stmt.setDouble(3, loan.getAmount());
            stmt.setDouble(4, loan.getInterestRate());
            stmt.setInt(5, loan.getRepaymentPeriod());
            stmt.setDouble(6, loan.getMonthlyRepayment());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
