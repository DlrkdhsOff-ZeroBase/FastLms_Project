package com.zero.fastlms.course.model;

import com.zero.fastlms.admin.model.CommonParam;
import lombok.Data;

@Data
public class CourseParam extends CommonParam {

    long id;
    long categoryId;

}
