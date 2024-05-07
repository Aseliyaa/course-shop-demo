package com.example.demo.service;

import com.example.demo.exception.CourseServiceException;
import com.example.demo.model.ConsultationForm;
import com.example.demo.model.Course;

public interface CourseService {

    Course findCourseById(long courseId) throws CourseServiceException;

    void addCourseToUserCart(long courseId, String login) throws CourseServiceException;

    void addFormToUser(String login, long courseId, ConsultationForm form) throws CourseServiceException;
}
