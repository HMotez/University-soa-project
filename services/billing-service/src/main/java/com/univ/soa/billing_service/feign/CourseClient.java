package com.univ.soa.billing_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service", url = "${feign.course.url:http://localhost:8082}")
public interface CourseClient {
    @GetMapping("/ws/courses/{id}") // placeholder for REST mapping if exists
    Object getCourseById(@PathVariable("id") Long id);
}
