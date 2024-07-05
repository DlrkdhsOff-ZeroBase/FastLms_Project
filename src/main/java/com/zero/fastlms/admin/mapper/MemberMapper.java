package com.zero.fastlms.admin.mapper;

import com.zero.fastlms.admin.dto.MemberDto;
import com.zero.fastlms.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    long selectListCount(MemberParam parameter);

    List<MemberDto> selectList(MemberParam parameter);
}
