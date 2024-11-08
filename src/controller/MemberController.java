package controller;

import model.DatabaseConnection;
import model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberController {
    //REGISTER MEMBER
    public static Member registerMember(Member member) {
        Member registeredMember = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Insert the new member into the database
            String sql = "INSERT INTO members (first_name, last_name, age, email, pass_word, registration_fee, total_shares) VALUES (?, ?, ?, ?, ?, ?, ?)";
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, member.getFirstName());
            stmt.setString(2, member.getLastName());
            stmt.setInt(3, member.getAge());
            stmt.setString(4, member.getEmail());
            stmt.setString(5, member.getPassword());
            stmt.setDouble(6, member.getRegistrationFee());
            stmt.setDouble(7, member.getTotalShares());
            stmt.executeUpdate();

            // Retrieve the generated member_id
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int memberId = generatedKeys.getInt(1);

                // Retrieve the newly registered member's details for login purposes
                registeredMember = getMemberData(memberId);
            }

            System.out.println("Member registered and logged in successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registeredMember;
    }

    //LOGIN MEMBER
    public static Member login(String email, String passwordInput) {
        Member member = null;

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT member_id, first_name, age, last_name, email, pass_word, registration_fee, total_shares FROM members WHERE email = ? AND pass_word = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, passwordInput);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("member_id");
                String firstName = rs.getString("first_name");
                int age = rs.getInt("age");
                String lastName = rs.getString("last_name");
                String dbEmail = rs.getString("email");
                String dbPassword = rs.getString("pass_word");
                double registrationFee = rs.getDouble("registration_fee");
                double totalShares = rs.getDouble("total_shares");

                // Instantiate Member only if credentials match
                if (dbEmail.equals(email) && dbPassword.equals(passwordInput)) {
                    member = new Member(age, firstName, lastName, dbEmail, dbPassword, registrationFee, totalShares);
                }
            } else {
                System.out.println("Login failed: Invalid email or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    //GET MEMBER DATA BY MEMBER ID
    public static Member getMemberData(int memberId) {
        Member member = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT member_id, first_name, age, last_name, email, pass_word, registration_fee, total_shares FROM members WHERE member_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, memberId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("member_id");
                String firstName = rs.getString("first_name");
                int age = rs.getInt("age");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String password = rs.getString("pass_word");
                double registrationFee = rs.getDouble("registration_fee");
                double totalShares = rs.getDouble("total_shares");

                member = new Member(age, firstName, lastName, email, password, registrationFee, totalShares);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    //GET ALL MEMBERS
    public static ArrayList<Member> getAllUsers() {
        ArrayList<Member> members = new ArrayList<Member>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT member_id, first_name, age, last_name, email, pass_word, registration_fee, total_shares FROM members";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("member_id");
                String firstName = rs.getString("first_name");
                int age = rs.getInt("age");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String password = rs.getString("pass_word");
                double registrationFee = rs.getDouble("registration_fee");
                double totalShares = rs.getDouble("total_shares");

                Member member = new Member(age, firstName, lastName, email, password, registrationFee, totalShares);
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    //DELETE ALL MEMBERS
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

    //GET LAST MEMBER ID
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
