package com.zero.fastlms.member.service;

import com.zero.fastlms.member.model.MemberInput;
import com.zero.fastlms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    boolean emailAuth(String uuid);

    boolean sendResetPassword(ResetPasswordInput parameter);

    boolean resetPassword(String id, String password);

    boolean checkResetPassword(String uuid);
}
