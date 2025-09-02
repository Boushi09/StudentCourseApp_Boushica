package com.project.dao;

import com.project.model.Student;
import com.project.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {

    private boolean emailExists(String email) {
        String sql = "SELECT id FROM students WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException("⚠️ Failed to check email existence.");
        }
    }

    public boolean addStudent(Student student) {
        if (emailExists(student.getEmail())) {
            System.out.println("⚠️ Email already exists: " + student.getEmail());
            return false;
        }

        String sql = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPassword());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to add student. Please try again.");
        }
    }

    public void getAllStudents() {
        String sql = "SELECT * FROM students";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println(id + " | " + name + " | " + email);
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to fetch students.");
        }
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                return new Student(id, name, email, password);
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to fetch student by ID.");
        }
        return null;
    }

    public boolean updateStudent(int id, Student updatedStudent) {
        String sql = "UPDATE students SET name = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, updatedStudent.getName());
            stmt.setString(2, updatedStudent.getEmail());
            stmt.setString(3, updatedStudent.getPassword());
            stmt.setInt(4, id);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to update student.");
        }
    }

    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to delete student.");
        }
    }
}
