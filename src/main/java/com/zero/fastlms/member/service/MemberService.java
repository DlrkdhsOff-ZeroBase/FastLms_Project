package com.zero.fastlms.member.service;

import com.zero.fastlms.admin.dto.MemberDto;
import com.zero.fastlms.admin.model.MemberParam;
import com.zero.fastlms.member.model.MemberInput;
import com.zero.fastlms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    boolean emailAuth(String uuid);

    boolean sendResetPassword(ResetPasswordInput parameter);

    boolean resetPassword(String id, String password);

    boolean checkResetPassword(String uuid);

    List<MemberDto> getList(MemberParam parameter);
}
