package controller;

import model.DatabaseConnection;
import model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanController {
    public static Loan applyForLoan(Loan loan) {
        System.out.println("apply loan called");
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Start transaction
            conn.setAutoCommit(false);

            // Insert loan into the loans table
            String loanSql = "INSERT INTO loans (member_id, loan_type, amount, interest_rate, repayment_period, monthly_repayment) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement loanStmt = conn.prepareStatement(loanSql, PreparedStatement.RETURN_GENERATED_KEYS);
            loanStmt.setInt(1, loan.getMemberId());
            loanStmt.setString(2, loan.getLoanType());
            loanStmt.setDouble(3, loan.getAmount());
            loanStmt.setDouble(4, loan.getInterestRate());
            loanStmt.setInt(5, loan.getRepaymentPeriod());
            loanStmt.setDouble(6, loan.getMonthlyRepayment());

            int affectedRows = loanStmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated ID for the loan
                ResultSet generatedKeys = loanStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int loanId = generatedKeys.getInt(1);  // The generated loan ID
                    loan.setId(loanId);  // Set the generated loan ID on the Loan object

                    // Insert guarantor into the guarantors table
                    String guarantorSql = "INSERT INTO guarantors (loan_id, guarantor_member_id, amount_guaranteed) VALUES (?, ?, ?)";
                    PreparedStatement guarantorStmt = conn.prepareStatement(guarantorSql);
                    guarantorStmt.setInt(1, loanId);
                    guarantorStmt.setInt(2, loan.getGuarantorID());
                    guarantorStmt.setDouble(3, loan.getGuaranteedAmount());

                    int guarantorAffectedRows = guarantorStmt.executeUpdate();
                    if (guarantorAffectedRows > 0) {
                        // Commit transaction if both inserts are successful
                        conn.commit();
                        System.out.println("Loan and guarantor applied successfully.");
                        return loan;  // Return the loan object with the assigned loan ID
                    } else {
                        conn.rollback();
                        System.out.println("Guarantor creation failed, rolling back transaction.");
                    }
                }
            } else {
                conn.rollback();
                System.out.println("Loan application failed, rolling back transaction.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return null if something goes wrong
    }
}
