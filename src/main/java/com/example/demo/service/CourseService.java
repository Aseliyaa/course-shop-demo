package com.example.demo.service;

import com.example.demo.exception.CourseServiceException;
import com.example.demo.model.Category;
import com.example.demo.model.ConsultationForm;
import com.example.demo.model.Course;

import java.util.List;

public interface CourseService {

    Course findCourseById(long courseId) throws CourseServiceException;

    void addCourseToUserCart(long courseId, String login) throws CourseServiceException;

    void addFormToUser(String login, long courseId, ConsultationForm form) throws CourseServiceException;

    void updateCourse(Course course) throws CourseServiceException;

    void deleteCourseFromCategory(long courseId, long categoryId) throws CourseServiceException;

    void deleteCourse(long courseId) throws CourseServiceException;

    List<Course> findAllCourses() throws CourseServiceException;

    void saveCourse(Course course) throws CourseServiceException;


    List<Course> searchCoursesForCategory(String query, Category category) throws CourseServiceException;
}
