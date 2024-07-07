package com.zero.fastlms.course.service;

import com.zero.fastlms.course.dto.TakeCourseDto;
import com.zero.fastlms.course.model.ServiceResult;
import com.zero.fastlms.course.model.TakeCourseParam;

import java.util.List;

public interface TakeCourseService {

    List<TakeCourseDto> list(TakeCourseParam parameter);

    ServiceResult updateStatus(long id, String status);
}
