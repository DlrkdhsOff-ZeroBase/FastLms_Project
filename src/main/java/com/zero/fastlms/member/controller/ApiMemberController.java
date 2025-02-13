package com.zero.fastlms.member.controller;

import com.zero.fastlms.common.model.ResponseResult;
import com.zero.fastlms.course.dto.TakeCourseDto;
import com.zero.fastlms.course.model.ServiceResult;
import com.zero.fastlms.course.model.TakeCourseInput;
import com.zero.fastlms.course.service.TakeCourseService;
import com.zero.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiMemberController {

    private final MemberService memberService;

    private final TakeCourseService takeCourseService;

    @PostMapping("/api/member/course/cancel.api")
    public ResponseEntity<?> cancelCourse(@RequestBody TakeCourseInput parameter, Model model, Principal principal) {

        String userId = principal.getName();
        TakeCourseDto detail = takeCourseService.detail(parameter.getTakeCourseId());

        if (detail == null) {
            ResponseResult responseResult = new ResponseResult(false, "수강 신청 정보가 존재하지 않습니다.");
            return ResponseEntity.ok().body(responseResult);
        }

        if (userId == null || !userId.equals(detail.getUserId())) {
            ResponseResult responseResult = new ResponseResult(false, "본인의 수강 신청 정보만 취소할 수 있습니다.");
            return ResponseEntity.ok().body(responseResult);
        }

        ServiceResult result = takeCourseService.cancel(parameter.getTakeCourseId());
        if (!result.isResult()) {
            ResponseResult responseResult = new ResponseResult(false, result.getMessage());
            return ResponseEntity.ok().body(responseResult);
        }

        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }
}
