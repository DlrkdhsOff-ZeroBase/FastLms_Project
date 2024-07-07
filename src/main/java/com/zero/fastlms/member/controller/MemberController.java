package com.zero.fastlms.member.controller;

import com.zero.fastlms.admin.dto.MemberDto;
import com.zero.fastlms.course.dto.TakeCourseDto;
import com.zero.fastlms.course.model.ServiceResult;
import com.zero.fastlms.course.service.TakeCourseService;
import com.zero.fastlms.member.model.MemberInput;
import com.zero.fastlms.member.model.ResetPasswordInput;
import com.zero.fastlms.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final TakeCourseService takeCourseService;

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
    public String memberInfo(Principal principal, Model model) {
        String userId = principal.getName();

        MemberDto detail = memberService.detail(userId);
        model.addAttribute("detail", detail);

        return "/member/info";
    }


    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            System.out.println("errorMessage = " + errorMessage);
        }
        return "member/login";
    }

    @GetMapping("/find/password")
    public String findPassword() {
        return "member/find_password";
    }

    @PostMapping("/find/password")
    public String findPasswordSubmit(Model model, ResetPasswordInput parameter) {
        boolean result = false;

        try {
            result = memberService.sendResetPassword(parameter);
        } catch (Exception e) {

        }
        model.addAttribute("result", result);

        return "member/find_password_result";
    }

    @GetMapping("/reset/password")
    public String resetPassword(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("id");

        boolean result = memberService.checkResetPassword(uuid);
        model.addAttribute("result", result);

        return "member/reset_password";
    }

    @PostMapping("/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {

        boolean result = false;
        try {
            result = memberService.resetPassword(parameter.getId(), parameter.getPassword());
        } catch (Exception e) {
        }

        model.addAttribute("result", result);

        return "member/reset_password_result";
    }

    @GetMapping("/updatePassword")
    public String updatePassword() {
        return "member/updatePassword";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(Model model, MemberInput parameter, Principal principal) {
        System.out.println("MemberController.updatePassword");

        parameter.setUserId(principal.getName());
        ServiceResult result = memberService.updateMemberPassword(parameter);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }

    @PostMapping("/info")
    public String updateMember(Principal principal, Model model, MemberInput parameter) {
        parameter.setUserId(principal.getName());

        ServiceResult result = memberService.updateMember(parameter);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }


    @GetMapping("/myTakeCourse")
    public String myTakeCourse(Model model, Principal principal) {

        String userId = principal.getName();

        List<TakeCourseDto> list = takeCourseService.myCourse(userId);

        model.addAttribute("list", list);
        return "member/myTakeCourse";
    }
}
