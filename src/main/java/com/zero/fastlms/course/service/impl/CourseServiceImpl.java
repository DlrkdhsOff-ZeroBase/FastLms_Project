package com.zero.fastlms.course.service.impl;

import com.zero.fastlms.course.dto.CourseDto;
import com.zero.fastlms.course.entity.Course;
import com.zero.fastlms.course.mapper.CourseMapper;
import com.zero.fastlms.course.model.CourseInput;
import com.zero.fastlms.course.model.CourseParam;
import com.zero.fastlms.course.repository.CourseRepository;
import com.zero.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;
    @Override
    public boolean add(CourseInput parameter) {

        Course course = Course.builder()
                .categoryId(parameter.getCategoryId())
                .subject(parameter.getSubject())
                .regDt(LocalDateTime.now())
                .build();

        courseRepository.save(course);

        return true;
    }

    @Override
    public boolean set(CourseInput parameter) {
        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if (optionalCourse.isEmpty()) {
            return false;
        }

        Course course = optionalCourse.get();
        course.setCategoryId(parameter.getCategoryId());
        course.setSubject(parameter.getSubject());
        course.setUptDt(LocalDateTime.now());

        courseRepository.save(course);

        return true;
    }

    @Override
    public List<CourseDto> getList(CourseParam parameter) {
        long totalCount = courseMapper.selectListCount(parameter);

        List<CourseDto> list = courseMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (CourseDto dto : list) {
                dto.setTotalCount(totalCount);
                dto.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public CourseDto getById(long id) {
        return courseRepository.findById(id).map(CourseDto::of).orElse(null);
    }
}
