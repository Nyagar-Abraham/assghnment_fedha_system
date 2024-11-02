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
            String sql = "INSERT INTO members (first_name,age, last_name, registration_fee, total_shares) VALUES (?, ?, ?, ?,?)";
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, member.getFirstName());
            stmt.setInt(2, member.getAge());
            stmt.setString(3, member.getLastName());
            stmt.setDouble(4, member.getRegistrationFee());
            stmt.setDouble(5, member.getTotalShares());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Member getMemberData(int memberId) {
        Member member = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT member_id, first_name, age, last_name, registration_fee, total_shares FROM members WHERE member_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, memberId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("member_id");
                String firstName = rs.getString("first_name");
                int age = rs.getInt("age");
                String lastName = rs.getString("last_name");
                double registrationFee = rs.getDouble("registration_fee");
                double totalShares = rs.getDouble("total_shares");

                member = new Member(id, age, firstName, lastName, registrationFee, totalShares);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    public static void deleteAllMembers() {
        String query = "DELETE FROM members";

        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                int rowsDeleted = stmt.executeUpdate();
                System.out.println(rowsDeleted + " members deleted from the database.");

            }
        } catch (SQLException e) {
            System.out.println("Error deleting all members: " + e.getMessage());
        }
    }


    public static int getLastMemberId() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT MAX(member_id) FROM members";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getObject(1) != null) {
                System.out.println(rs.getObject(1));
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
