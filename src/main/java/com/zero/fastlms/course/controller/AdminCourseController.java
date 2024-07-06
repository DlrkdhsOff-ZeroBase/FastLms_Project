package com.zero.fastlms.course.controller;

import com.zero.fastlms.admin.dto.MemberDto;
import com.zero.fastlms.course.dto.CourseDto;
import com.zero.fastlms.course.model.CourseInput;
import com.zero.fastlms.course.model.CourseParam;
import com.zero.fastlms.course.service.CourseService;
import com.zero.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/course")
public class AdminCourseController extends BaseController{

    private final CourseService courseService;

    @GetMapping("/list.do")
    public String list(Model model, CourseParam parameter) {
        parameter.init();

        List<CourseDto> courseList = courseService.getList(parameter);
        System.out.println("courseList.toString() = " + courseList.toString());

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(courseList)) {
            totalCount = courseList.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);


        return "admin/course/list";
    }

    @GetMapping("/add.do")
    public String add(Model model) {
        return "admin/course/add";
    }

    @PostMapping("/add.do")
    public String addSubmit(Model model, CourseInput parameter) {
        boolean result = courseService.add(parameter);

        return "redirect:/admin/course/list.do";
    }
}
