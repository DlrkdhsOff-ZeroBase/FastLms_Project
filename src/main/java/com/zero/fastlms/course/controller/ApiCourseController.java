package com.zero.fastlms.course.controller;

import com.zero.fastlms.course.model.TakeCourseInput;
import com.zero.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ApiCourseController {

    private final CourseService courseService;

    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReq(Model model,
                                       @RequestBody TakeCourseInput parameter,
                                       Principal principal) {
        parameter.setUserId(principal.getName());

        boolean result = courseService.req(parameter);
        if (!result) {
            return ResponseEntity.badRequest().body("수강신청에 실패하였습니다.");
        }

        return ResponseEntity.ok().body(parameter);
    }
}
