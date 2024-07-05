package com.zero.fastlms.admin;

import com.zero.fastlms.admin.dto.MemberDto;
import com.zero.fastlms.member.entity.Member;
import com.zero.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list.do")
    public String memberList(Model model) {

        List<MemberDto> list = memberService.getList();
        model.addAttribute("list", list);

        return "admin/member/list";
    }
}
