package com.zero.fastlms.course.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TakeCourseDto {
    Long id;

    long courseId;
    String userId;

    long payPrice;
    String status;

    LocalDateTime regDt;

    String userName;
    String phone;
    String subject;

    long totalCount;
    long seq;
}
