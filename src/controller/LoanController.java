package controller;

import model.DatabaseConnection;
import model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanController {
   // APPLY LOAN
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


    public static List<Loan> getUserLoansWithGuarantor(int memberId) {
        List<Loan> loans = new ArrayList<>();

        String sql = """
            SELECT loans.loan_id, loans.member_id, loans.loan_type, loans.amount, 
                   loans.interest_rate, loans.repayment_period, loans.monthly_repayment,
                   guarantors.guarantor_member_id, guarantors.amount_guaranteed,
                   members.first_name AS guarantor_name
            FROM loans
            LEFT JOIN guarantors ON loans.loan_id = guarantors.loan_id
            LEFT JOIN members ON guarantors.guarantor_member_id = members.member_id
            WHERE loans.member_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Loan loan = new Loan(
                        rs.getInt("loan_id"),
                        rs.getInt("member_id"),
                        rs.getString("loan_type"),
                        rs.getDouble("amount"),
                        rs.getDouble("interest_rate"),
                        rs.getInt("repayment_period"),
                        rs.getDouble("monthly_repayment")
                );

                // Assuming Loan has methods to set guarantor details
                loan.setGuarantorID(rs.getInt("guarantor_member_id"));
                loan.setGuaranteedAmount(rs.getDouble("amount_guaranteed"));
                loan.setGuarantorName(rs.getString("guarantor_name"));

                loans.add(loan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }


    //DELETE LOAN
    public static boolean deleteLoanById(int loanId) {
        String loanSql = "DELETE FROM loans WHERE loan_id = ?";
        String guarantorSql = "DELETE FROM guarantors WHERE loan_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Start transaction
            conn.setAutoCommit(false);

            // Delete guarantor record
            try (PreparedStatement guarantorStmt = conn.prepareStatement(guarantorSql)) {
                guarantorStmt.setInt(1, loanId);
                int guarantorAffectedRows = guarantorStmt.executeUpdate();

                if (guarantorAffectedRows > 0) {
                    // Delete loan record
                    try (PreparedStatement loanStmt = conn.prepareStatement(loanSql)) {
                        loanStmt.setInt(1, loanId);
                        int loanAffectedRows = loanStmt.executeUpdate();

                        if (loanAffectedRows > 0) {
                            // Commit transaction if both deletions are successful
                            conn.commit();
                            System.out.println("Loan and associated guarantor deleted successfully.");
                            return true;
                        } else {
                            conn.rollback();
                            System.out.println("Loan deletion failed, rolling back transaction.");
                        }
                    }
                } else {
                    conn.rollback();
                    System.out.println("Guarantor deletion failed, rolling back transaction.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Return false if the deletion fails
    }

}
