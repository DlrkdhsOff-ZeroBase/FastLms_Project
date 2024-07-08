package com.zero.fastlms.course.controller;

import com.zero.fastlms.admin.service.CategoryService;
import com.zero.fastlms.course.dto.CourseDto;
import com.zero.fastlms.course.model.CourseInput;
import com.zero.fastlms.course.model.CourseParam;
import com.zero.fastlms.course.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/course")
public class AdminCourseController extends BaseController{

    private final CourseService courseService;

    private final CategoryService categoryService;

    @GetMapping("/list.do")
    public String list(Model model, CourseParam parameter) {
        parameter.init();

        List<CourseDto> courseList = courseService.getList(parameter);

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

    @GetMapping(value = {"/add.do", "edit.do"})
    public String add(Model model, HttpServletRequest request, CourseInput parameter) {

        model.addAttribute("category", categoryService.list());

        boolean editMode = request.getRequestURI().contains("/edit.do");
        CourseDto detail = new CourseDto();
        if (editMode) {
            long id = parameter.getId();

            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existCourse;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);
        return "admin/course/add";
    }

    String getNewSaveFile(String basePath, String originalFileName) {
        LocalDate now = LocalDate.now();
        String[] dirs = {
                    String.format("%s/%d/", basePath, now.getYear()),
                    String.format("%s/%d/%02d/", basePath, now.getYear(), now.getMonthValue()),
                    String.format("%s/%d/%02d/%02d/", basePath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFileName != null) {
            int dotPos = originalFileName.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFileName.substring(dotPos + 1);
            }
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFile = String.format("%s%s", dirs[2], uuid);
        if (!fileExtension.isEmpty()) {
            newFile += "." + fileExtension;
        }
        return newFile;
    }

    @PostMapping(value = {"/add.do", "edit.do"})
    public String addSubmit(Model model, HttpServletRequest request, CourseInput parameter, MultipartFile file) {

        String saveFileName = "";
        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            String basePath = "/Users/kaon/Study/ZeroBase/project/ZB_FastLms_Project/files/";
            saveFileName = getNewSaveFile(basePath, originalFileName);

            try {
                File newFile = new File(saveFileName);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }

        parameter.setFileName(saveFileName);

        boolean editMode = request.getRequestURI().contains("/edit.do");
        if (editMode) {
            long id = parameter.getId();

            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = courseService.set(parameter);
        } else {
            boolean result = courseService.add(parameter);
        }


        return "redirect:/admin/course/list.do";
    }

    @PostMapping("delete.do")
    public String delete(Model model, HttpServletRequest request, CourseInput parameter) {

        boolean result = courseService.delete(parameter.getIdList());

        return "redirect:/admin/course/list.do";
    }
}
