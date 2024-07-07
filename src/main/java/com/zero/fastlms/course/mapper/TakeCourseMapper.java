package com.zero.fastlms.course.mapper;

import com.zero.fastlms.course.dto.CourseDto;
import com.zero.fastlms.course.dto.TakeCourseDto;
import com.zero.fastlms.course.model.CourseParam;
import com.zero.fastlms.course.model.TakeCourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TakeCourseMapper {

    long selectListCount(TakeCourseParam parameter);

    List<TakeCourseDto> selectList(TakeCourseParam parameter);

    List<TakeCourseDto> selectListMyCourse(TakeCourseParam parameter);


}
