package controller;

import model.DatabaseConnection;
import model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberController {
    public static void registerMember(Member member) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO members (first_name, last_name, registration_fee, total_shares) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, member.getFirstName());
            stmt.setString(2, member.getLastName());
            stmt.setDouble(3, member.getRegistrationFee());
            stmt.setDouble(4, member.getTotalShares());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getLastMemberId() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT MAX(id) FROM members";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
