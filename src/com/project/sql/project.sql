-- 1️⃣ Create database
CREATE DATABASE IF NOT EXISTS course_db;
USE course_db;

-- 2️⃣ Create students table
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- 3️⃣ Create courses table
CREATE TABLE IF NOT EXISTS courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    duration INT NOT NULL,
    fee DECIMAL(10,2) NOT NULL
);

-- 4️⃣ Create enrollments table
CREATE TABLE IF NOT EXISTS enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT unique_enrollment UNIQUE (student_id, course_id, enrollment_date)
);

-- 5️⃣ Insert sample students (ignore duplicates)
INSERT IGNORE INTO students (name, email, password)
VALUES 
('Boushica', 'boushica@gmail.com', '12345'),
('Arun', 'arun@gmail.com', 'pass123');

-- 6️⃣ Insert sample courses (ignore duplicates)
INSERT IGNORE INTO courses (name, description, duration, fee)
VALUES 
('Java Basics', 'Intro to Java Programming', 3, 5000.00),
('SQL Fundamentals', 'Learn SQL Queries and Joins', 2, 4000.00);

-- 7️⃣ Insert sample enrollment (ignore duplicates)
INSERT IGNORE INTO enrollments (student_id, course_id, enrollment_date)
VALUES (1, 1, CURDATE());

-- 8️⃣ View all data
SELECT * FROM students;
SELECT * FROM courses;
SELECT * FROM enrollments;
DESCRIBE students;
