package com.university.course.course_service.repository;

import com.university.course.course_service.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByGroupe(String groupe);
}
