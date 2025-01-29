package dao;

import model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    private static final String URL = "jdbc:mysql://localhost:3333/student_management";
    private static final String USER = "ap10er";
    private static final String PASSWORD = "sasawa1212";

    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM `Groups`"; // Используем обратные кавычки
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                groups.add(new Group(
                        rs.getLong("id"),
                        rs.getString("group_number"),
                        rs.getString("faculty_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public void addGroup(Group group) {
        String sql = "INSERT INTO `Groups` (group_number, faculty_name) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, group.getGroupNumber());
            stmt.setString(2, group.getFacultyName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGroup(Group group) {
        String sql = "UPDATE `Groups` SET group_number = ?, faculty_name = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, group.getGroupNumber());
            stmt.setString(2, group.getFacultyName());
            stmt.setLong(3, group.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGroup(Long id) {
        String sql = "DELETE FROM `Groups` WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}