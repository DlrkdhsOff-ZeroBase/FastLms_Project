package com.zero.fastlms.member.service;

import com.zero.fastlms.member.model.MemberInput;
import org.springframework.stereotype.Service;

public interface MemberService {

    boolean register(MemberInput parameter);
}
