package com.zero.fastlms.admin.mapper;

import com.zero.fastlms.admin.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<CategoryDto> select(CategoryDto parameter);
}
