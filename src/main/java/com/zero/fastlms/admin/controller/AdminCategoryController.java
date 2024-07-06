package com.zero.fastlms.admin.controller;

import com.zero.fastlms.admin.dto.CategoryDto;
import com.zero.fastlms.admin.model.CategoryInput;
import com.zero.fastlms.admin.model.MemberParam;
import com.zero.fastlms.admin.service.CategoryService;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list.do")
    public String list(Model model) {
        List<CategoryDto> list = categoryService.list();
        model.addAttribute("list", list);

        return "admin/category/list";
    }

    @PostMapping("/add.do")
    public String add(Model mode, CategoryInput parameter) {

        boolean result = categoryService.add(parameter.getCategoryName());

        return "redirect:/admin/category/list.do";
    }

    @PostMapping("/delete.do")
    public String delete(Model model, CategoryInput parameter) {
        boolean result = categoryService.delete(parameter.getId());

        return "redirect:/admin/category/list.do";
    }

    @PostMapping("/update.do")
    public String update(Model model, CategoryInput parameter) {
        boolean result = categoryService.update(parameter);

        return "redirect:/admin/category/list.do";
    }
}
