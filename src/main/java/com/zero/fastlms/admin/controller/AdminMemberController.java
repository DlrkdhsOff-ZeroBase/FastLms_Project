package com.zero.fastlms.admin.controller;

import com.zero.fastlms.admin.dto.MemberDto;
import com.zero.fastlms.admin.model.MemberParam;
import com.zero.fastlms.admin.model.MemberStatusInput;
import com.zero.fastlms.member.service.MemberService;
import com.zero.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/member")
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/list.do")
    public String memberList(Model model, MemberParam parameter) {
        parameter.init();

        List<MemberDto> members = memberService.getList(parameter);

        long totalCount = 0;
        if (members != null && !members.isEmpty()) {
            totalCount = members.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        PageUtil pageUtil = new PageUtil(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pageUtil.pager());

        return "admin/member/list";
    }

    @GetMapping("/detail.do")
    public String detail(Model model, MemberParam parameter) {
        parameter.init();

        MemberDto member = memberService.detail(parameter.getUserId());
        model.addAttribute("member", member);

        return "admin/member/detail";
    }

    @PostMapping("/status.do")
    public String status(Model mode, MemberStatusInput parameter) {
       boolean result =  memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());

        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }

}
