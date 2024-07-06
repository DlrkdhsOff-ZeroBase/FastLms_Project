package com.zero.fastlms.course.service;

import com.zero.fastlms.course.dto.CourseDto;
import com.zero.fastlms.course.model.CourseInput;
import com.zero.fastlms.course.model.CourseParam;

import java.util.List;

public interface CourseService {
    boolean add(CourseInput parameter);

    boolean set(CourseInput parameter);

    List<CourseDto> getList(CourseParam parameter);

    CourseDto getById(long id);

}
