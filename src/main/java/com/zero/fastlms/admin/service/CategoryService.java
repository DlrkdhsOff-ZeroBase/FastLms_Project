package com.zero.fastlms.admin.service;

import com.zero.fastlms.admin.dto.CategoryDto;
import com.zero.fastlms.admin.model.CategoryInput;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> list();

    boolean add(String categoryName);

    boolean update(CategoryInput parameter);

    boolean delete(Long id);

    List<CategoryDto> frontList(CategoryDto parameter);
}
