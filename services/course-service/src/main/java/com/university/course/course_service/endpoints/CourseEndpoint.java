package com.university.course.course_service.endpoints;

import com.university.course.course_service.entity.Course;
import com.university.course.course_service.repository.CourseRepository;
import com.university.course.ws.*;
import org.springframework.ws.server.endpoint.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Endpoint
public class CourseEndpoint {

    private static final String NAMESPACE = "http://university.com/courses";

    private final CourseRepository repository;

    public CourseEndpoint(CourseRepository repository) {
        this.repository = repository;
    }

    // ============================
    // 1. GET COURSE BY ID
    // ============================
    @PayloadRoot(namespace = NAMESPACE, localPart = "getCourseRequest")
    @ResponsePayload
    public GetCourseResponse getCourse(@RequestPayload GetCourseRequest request) {
        GetCourseResponse response = new GetCourseResponse();
        Course course = repository.findById(request.getId()).orElse(null);
        if (course != null) {
            response.setCourse(convert(course));
        }
        return response;
    }

    // ============================
    // 2. LIST ALL COURSES
    // ============================
    @PayloadRoot(namespace = NAMESPACE, localPart = "listCoursesRequest")
    @ResponsePayload
    public ListCoursesResponse listCourses() {
        ListCoursesResponse response = new ListCoursesResponse();
        List<Course> courses = repository.findAll();
        courses.forEach(c -> response.getCourses().add(convert(c)));
        return response;
    }

    // ============================
    // 3. ADD COURSE
    // ============================
    @PayloadRoot(namespace = NAMESPACE, localPart = "addCourseRequest")
    @ResponsePayload
    public AddCourseResponse addCourse(@RequestPayload AddCourseRequest request) {
        AddCourseResponse response = new AddCourseResponse();

        Course course = new Course(
                null,
                request.getCode(),
                request.getTitle(),
                request.getTeacherId(),
                request.getNiveau(),
                request.getGroupe(),
                request.getHoraire()
        );

        Course saved = repository.save(course);
        response.setCourse(convert(saved));

        return response;
    }

    // ============================
    // 4. UPDATE COURSE
    // ============================
    @PayloadRoot(namespace = NAMESPACE, localPart = "updateCourseRequest")
    @ResponsePayload
    public UpdateCourseResponse updateCourse(@RequestPayload UpdateCourseRequest request) {
        UpdateCourseResponse response = new UpdateCourseResponse();

        Course existing = repository.findById(request.getId()).orElse(null);
        if (existing != null) {
            existing.setCode(request.getCode());
            existing.setTitle(request.getTitle());
            existing.setTeacherId(request.getTeacherId());
            existing.setNiveau(request.getNiveau());
            existing.setGroupe(request.getGroupe());
            existing.setHoraire(request.getHoraire());

            repository.save(existing);
            response.setCourse(convert(existing));
        }

        return response;
    }

    // ============================
    // 5. DELETE COURSE
    // ============================
    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteCourseRequest")
    @ResponsePayload
    public DeleteCourseResponse deleteCourse(@RequestPayload DeleteCourseRequest request) {
        DeleteCourseResponse response = new DeleteCourseResponse();

        boolean exists = repository.existsById(request.getId());
        response.setSuccess(exists);

        if (exists) {
            repository.deleteById(request.getId());
        }

        return response;
    }

    // ============================
    // Utility: convert JPA Entity → JAXB Course object
    // ============================
    private com.university.course.ws.Course convert(Course course) {
        com.university.course.ws.Course c = new com.university.course.ws.Course();
        c.setId(course.getId());
        c.setCode(course.getCode());
        c.setTitle(course.getTitle());
        c.setTeacherId(course.getTeacherId());
        c.setNiveau(course.getNiveau());
        c.setGroupe(course.getGroupe());
        c.setHoraire(course.getHoraire());
        return c;
    }
}