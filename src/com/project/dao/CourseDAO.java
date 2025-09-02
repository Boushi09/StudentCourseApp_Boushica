package com.project.dao;

import com.project.model.Course;
import com.project.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseDAO {

    public boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (name, description, duration, fee) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getName());
            stmt.setString(2, course.getDescription());
            stmt.setInt(3, course.getDuration());
            stmt.setDouble(4, course.getFee());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to add course.");
        }
    }

    public void getAllCourses() {
        String sql = "SELECT * FROM courses";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int duration = rs.getInt("duration");
                double fee = rs.getDouble("fee");

                System.out.printf("%d | %s | %d months | ₹%.2f%n", id, name, duration, fee);
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to fetch courses.");
        }
    }

    public boolean updateCourse(int id, Course course) {
        String sql = "UPDATE courses SET name = ?, description = ?, duration = ?, fee = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getName());
            stmt.setString(2, course.getDescription());
            stmt.setInt(3, course.getDuration());
            stmt.setDouble(4, course.getFee());
            stmt.setInt(5, id);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to update course.");
        }
    }

    public boolean deleteCourse(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to delete course.");
        }
    }
}
