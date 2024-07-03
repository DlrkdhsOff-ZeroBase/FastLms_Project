package com.zero.fastlms.member.service.impl;

import com.zero.fastlms.components.MailComponents;
import com.zero.fastlms.member.entity.Member;
import com.zero.fastlms.member.model.MemberInput;
import com.zero.fastlms.member.repository.MemberRepository;
import com.zero.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());

        if(optionalMember.isPresent()) {
            return false;
        }
        String uuid = UUID.randomUUID().toString();

        memberRepository.save(Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .password(parameter.getPassword())
                .phone(parameter.getPhone())
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build());

        String email = parameter.getUserId();
        String subject = "fastlms 사이트 가입을 축하드립니다.";
        String text = "<p>fastlms 사이트 가입을 축하드립니다.</p>" +
                "<p>아래 링크를 클릭하셔서 가입을 완료 하세요.</p>" +
                "<div><a target='_blank' href='http://localhost:8080/member/email-auth?id=" + uuid + "'>가입 완료</a></div>";

        mailComponents.sendMail(email, subject, text);
        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);

        if (optionalMember.isEmpty()) {
            return false;
        }
        Member member = optionalMember.get();
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());

        memberRepository.save(member);
        return true;
    }
}
