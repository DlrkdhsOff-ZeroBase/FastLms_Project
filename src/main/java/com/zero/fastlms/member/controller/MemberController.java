package com.zero.fastlms.member.controller;

import com.zero.fastlms.member.model.MemberInput;
import com.zero.fastlms.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registerSubmit(Model model, HttpServletRequest request, HttpServletResponse response, MemberInput parameter) {

        boolean result = memberService.register(parameter);

        model.addAttribute("result", result);

        return "member/register_complete";
    }

    @GetMapping("/email-auth")
    public String emailAuth(@RequestParam String id, Model model) {

        boolean result = memberService.emailAuth(id);
        model.addAttribute("result", result);

        return "member/email_auth";
    }

    @GetMapping("/info")
    public String memberInfo() {
        return "/member/info";
    }

    //    @RequestMapping("/login")
//    public String login(HttpServletRequest request, Model model) {
//        if (request.getParameter("errorMessage") != null) {
//            System.out.println(request.getParameter("errorMessage") );
//            model.addAttribute("errorMessage", request.getParameter("errorMessage") );
//        }
//
//        return "/member/login";
//    }
    @RequestMapping("/login")
    public String login() {

        return "/member/login";
    }

}
