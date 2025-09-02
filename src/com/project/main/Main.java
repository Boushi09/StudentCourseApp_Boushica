package com.project.main;

import com.project.dao.CourseDAO;
import com.project.dao.StudentDAO;
import com.project.model.Course;
import com.project.model.Student;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();
    private static final CourseDAO courseDAO = new CourseDAO();

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n🎓 Student & Course Management System");
                System.out.println("1. ➕ Add Student");
                System.out.println("2. 📂 View All Students");
                System.out.println("3. 🔍 Search Student by ID");
                System.out.println("4. ✏️ Update Student");
                System.out.println("5. 🗑️ Delete Student");
                System.out.println("6. 📚 Course Management");
                System.out.println("7. ❌ Exit");
                System.out.print("Enter your choice: ");

                int choice = getValidatedInt();

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> studentDAO.getAllStudents();
                    case 3 -> searchStudentById();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
                    case 6 -> courseMenu();
                    case 7 -> {
                        System.out.println("👋 Exiting... Goodbye!");
                        return;
                    }
                    default -> System.out.println("❗ Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
                sc.nextLine(); // clear buffer
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter name: ");
        String name = getValidatedString();

        System.out.print("Enter email: ");
        String email = getValidatedEmail();

        System.out.print("Enter password: ");
        String password = getValidatedString();

        studentDAO.addStudent(new Student(name, email, password));
    }

    private static void searchStudentById() {
        System.out.print("Enter student ID: ");
        int id = getValidatedInt();

        Student student = studentDAO.getStudentById(id);
        if (student != null) {
            System.out.println("✅ Student Found: " + student.getName() + " | " + student.getEmail());
        } else {
            System.out.println("❌ Student not found.");
        }
    }

    private static void updateStudent() {
        System.out.print("Enter student ID to update: ");
        int id = getValidatedInt();

        System.out.print("Enter new name: ");
        String name = getValidatedString();

        System.out.print("Enter new email: ");
        String email = getValidatedEmail();

        System.out.print("Enter new password: ");
        String password = getValidatedString();

        boolean updated = studentDAO.updateStudent(id, new Student(name, email, password));
        System.out.println(updated ? "✅ Update successful." : "❌ Update failed.");
    }

    private static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        int id = getValidatedInt();

        boolean deleted = studentDAO.deleteStudent(id);
        System.out.println(deleted ? "✅ Delete successful." : "❌ Delete failed.");
    }

    private static void courseMenu() {
        while (true) {
            try {
                System.out.println("\n📚 Course Management");
                System.out.println("1. ➕ Add Course");
                System.out.println("2. 📂 View All Courses");
                System.out.println("3. ✏️ Update Course");
                System.out.println("4. 🗑️ Delete Course");
                System.out.println("5. 🔙 Back to Main Menu");
                System.out.print("Enter your choice: ");

                int choice = getValidatedInt();

                switch (choice) {
                    case 1 -> addCourse();
                    case 2 -> courseDAO.getAllCourses();
                    case 3 -> updateCourse();
                    case 4 -> deleteCourse();
                    case 5 -> {
                        return;
                    }
                    default -> System.out.println("❗ Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
                sc.nextLine(); // clear buffer
            }
        }
    }

    private static void addCourse() {
        System.out.print("Enter course name: ");
        String name = getValidatedString();

        System.out.print("Enter description: ");
        String description = getValidatedString();

        System.out.print("Enter duration (months): ");
        int duration = getValidatedInt();

        System.out.print("Enter fee: ");
        double fee = getValidatedDouble();

        courseDAO.addCourse(new Course(name, description, duration, fee));
    }

    private static void updateCourse() {
        System.out.print("Enter course ID to update: ");
        int id = getValidatedInt();

        System.out.print("Enter new course name: ");
        String name = getValidatedString();

        System.out.print("Enter new description: ");
        String description = getValidatedString();

        System.out.print("Enter new duration (months): ");
        int duration = getValidatedInt();

        System.out.print("Enter new fee: ");
        double fee = getValidatedDouble();

        boolean updated = courseDAO.updateCourse(id, new Course(name, description, duration, fee));
        System.out.println(updated ? "✅ Update successful." : "❌ Update failed.");
    }

    private static void deleteCourse() {
        System.out.print("Enter course ID to delete: ");
        int id = getValidatedInt();

        boolean deleted = courseDAO.deleteCourse(id);
        System.out.println(deleted ? "✅ Delete successful." : "❌ Delete failed.");
    }

    // ----------------- Validation Methods -----------------

    private static int getValidatedInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid number. Try again: ");
            }
        }
    }

    private static double getValidatedDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid decimal number. Try again: ");
            }
        }
    }

    private static String getValidatedString() {
        while (true) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.print("❌ This field can't be empty. Try again: ");
        }
    }

    private static String getValidatedEmail() {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        while (true) {
            String email = sc.nextLine().trim();
            if (pattern.matcher(email).matches()) {
                return email;
            } else {
                System.out.print("❌ Invalid email format. Try again: ");
            }
        }
    }
}
