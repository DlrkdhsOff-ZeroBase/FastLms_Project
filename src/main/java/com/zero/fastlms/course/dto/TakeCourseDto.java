package com.zero.fastlms.course.dto;

import com.zero.fastlms.course.entity.TakeCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return regDt != null ? regDt.format(formatter) : "";
    }

    public static TakeCourseDto of(TakeCourse dto) {
        return TakeCourseDto.builder()
                .id(dto.getId())
                .courseId(dto.getCourseId())
                .userId(dto.getUserId())
                .payPrice(dto.getPayPrice())
                .status(dto.getStatus())
                .regDt(dto.getRegDt())
                .build();

    }
}
