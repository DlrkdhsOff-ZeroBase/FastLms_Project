package com.zero.fastlms.member.controller;

import com.zero.fastlms.member.entity.Member;
import com.zero.fastlms.member.model.MemberInput;
import com.zero.fastlms.member.repository.MemberRepository;
import com.zero.fastlms.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register() {

        return "member/register";
    }

    @PostMapping("/register")
    public String registerSubmit(HttpServletRequest request, HttpServletResponse response, MemberInput parameter) {

        boolean result = memberService.register(parameter);

        return "member/register_complete";
    }
}
