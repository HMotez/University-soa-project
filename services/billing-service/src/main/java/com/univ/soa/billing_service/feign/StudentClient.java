package com.univ.soa.billing_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Example: if student service exposes /students/{id}
@FeignClient(name = "student-service", url = "${feign.student.url:http://localhost:3001}")
public interface StudentClient {

    @GetMapping("/students/{id}")
    Object getStudentById(@PathVariable("id") Long id);
}
