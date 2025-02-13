package com.zero.fastlms.member.service;

import com.zero.fastlms.admin.dto.MemberDto;
import com.zero.fastlms.admin.model.MemberParam;
import com.zero.fastlms.course.model.ServiceResult;
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

    MemberDto detail(String userId);

    boolean updateStatus(String userId, String userStatus);

    boolean updatePassword(String userId, String password);

    ServiceResult updateMember(MemberInput parameter);

    ServiceResult updateMemberPassword(MemberInput parameter);

    ServiceResult withdraw(String userId, String password);
}
