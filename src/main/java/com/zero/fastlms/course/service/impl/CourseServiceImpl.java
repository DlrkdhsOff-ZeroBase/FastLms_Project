package com.zero.fastlms.course.service.impl;

import com.zero.fastlms.course.dto.CourseDto;
import com.zero.fastlms.course.entity.Course;
import com.zero.fastlms.course.entity.TakeCourse;
import com.zero.fastlms.course.entity.TakeCourseCode;
import com.zero.fastlms.course.mapper.CourseMapper;
import com.zero.fastlms.course.model.CourseInput;
import com.zero.fastlms.course.model.CourseParam;
import com.zero.fastlms.course.model.ServiceResult;
import com.zero.fastlms.course.model.TakeCourseInput;
import com.zero.fastlms.course.repository.CourseRepository;
import com.zero.fastlms.course.repository.TakeCourseRepository;
import com.zero.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final TakeCourseRepository takeCourseRepository;

    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean add(CourseInput parameter) {

        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());

        Course course = Course.builder()
                .categoryId(parameter.getCategoryId())
                .subject(parameter.getSubject())
                .keyword(parameter.getKeyword())
                .summary(parameter.getSummary())
                .contents(parameter.getContents())
                .price(parameter.getPrice())
                .salePrice(parameter.getSalePrice())
                .saleEndDt(saleEndDt)
                .regDt(LocalDateTime.now())
                .fileName(parameter.getFileName())
                .build();

        courseRepository.save(course);

        return true;
    }

    @Override
    public boolean set(CourseInput parameter) {

        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());

        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if (optionalCourse.isEmpty()) {
            return false;
        }

        Course course = optionalCourse.get();
        course.setCategoryId(parameter.getCategoryId());
        course.setSubject(parameter.getSubject());
        course.setKeyword(parameter.getKeyword());
        course.setSummary(parameter.getSummary());
        course.setContents(parameter.getContents());
        course.setPrice(parameter.getPrice());
        course.setSalePrice(parameter.getSalePrice());
        course.setSaleEndDt(saleEndDt);
        course.setUptDt(LocalDateTime.now());
        course.setFileName(parameter.getFileName());

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

    @Override
    public boolean delete(String idList) {

        if (idList != null && !idList.isEmpty()) {
            String[] ids = idList.split(",");
            for (String data : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(data);
                } catch (Exception e) {
                }

                if (id > 0) {
                    courseRepository.deleteById(id);
                }
            }
        }

        return true;
    }

    @Override
    public List<CourseDto> frontList(CourseParam parameter) {
        if (parameter.getCategoryId() < 1) {
            List<Course> courseList = courseRepository.findAll();
            return CourseDto.of(courseList);
        }

        Optional<List<Course>> optionalList = courseRepository.findByCategoryId(parameter.getCategoryId());

        return optionalList.map(CourseDto::of).orElse(null);

    }

    @Override
    public CourseDto frontDetail(long id) {

        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.map(CourseDto::of).orElse(null);
    }

    @Override
    public ServiceResult req(TakeCourseInput parameter) {

        ServiceResult result = new ServiceResult();

        Optional<Course> optionalCourse = courseRepository.findById(parameter.getCourseId());
        if (optionalCourse.isEmpty()) {
            result.setResult(false);
            result.setMessage("강좌 정보가 존재하지 않습니다.");
            return result;
        }

        Course course = optionalCourse.get();
        String[] statusList = {TakeCourseCode.STATUS_REQ, TakeCourseCode.STATUS_COMPLETE};
        long count = takeCourseRepository
                .countByCourseIdAndUserIdAndStatusIn(course.getId(), parameter.getUserId(), List.of(statusList));

        if (count > 0) {
            result.setResult(false);
            result.setMessage("이미 신청한 강좌 정보가 존재합니다.");
            return result;
        }

        TakeCourse takeCourse = TakeCourse.builder()
                .courseId(course.getId())
                .userId(parameter.getUserId())
                .payPrice(course.getSalePrice())
                .regDt(LocalDateTime.now())
                .status(TakeCourseCode.STATUS_REQ)
                .build();

        takeCourseRepository.save(takeCourse);

        result.setResult(true);
        result.setMessage("");

        return result;
    }

    @Override
    public List<CourseDto> listAll() {
        List<Course> list = courseRepository.findAll();
        return CourseDto.of(list);
    }
}
