package com.zero.fastlms.course.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceResult {

    boolean result;
    String message;



    public ServiceResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public ServiceResult(boolean result) {
        this.result = result;
    }
}
