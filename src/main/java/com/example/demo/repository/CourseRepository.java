package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c from Course c WHERE c.courseName LIKE CONCAT('%', :query, '%')")
    List<Course> searchCourses(String query);
}
