package com.zero.fastlms.member.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResetPasswordInput {

    private String userId;
    private String userName;

    private String id;
    private String password;
}
