package dao;

import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3333/student_management";
    private static final String USER = "ap10er";
    private static final String PASSWORD = "sasawa1212";

    public List<Student> getAllStudentsByGroupId(Long groupId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM `Students` WHERE group_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, groupId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getLong("group_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void addStudent(Student student) {
        String sql = "INSERT INTO `Students` (first_name, last_name, middle_name, birth_date, group_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getMiddleName());
            stmt.setDate(4, Date.valueOf(student.getBirthDate()));
            stmt.setLong(5, student.getGroupId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student student) {
        String sql = "UPDATE `Students` SET first_name = ?, last_name = ?, middle_name = ?, birth_date = ?, group_id = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getMiddleName());
            stmt.setDate(4, Date.valueOf(student.getBirthDate()));
            stmt.setLong(5, student.getGroupId());
            stmt.setLong(6, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(Long id) {
        String sql = "DELETE FROM `Students` WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
