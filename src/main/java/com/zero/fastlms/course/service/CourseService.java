package com.zero.fastlms.course.service;

import com.zero.fastlms.course.dto.CourseDto;
import com.zero.fastlms.course.model.CourseInput;
import com.zero.fastlms.course.model.CourseParam;
import com.zero.fastlms.course.model.ServiceResult;
import com.zero.fastlms.course.model.TakeCourseInput;

import java.util.List;

public interface CourseService {
    boolean add(CourseInput parameter);

    boolean set(CourseInput parameter);

    List<CourseDto> getList(CourseParam parameter);

    CourseDto getById(long id);

    boolean delete(String idList);


    List<CourseDto> frontList(CourseParam parameter);

    CourseDto frontDetail(long id);

    ServiceResult req(TakeCourseInput parameter);

    List<CourseDto> listAll();
}
