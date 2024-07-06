package com.zero.fastlms.admin.service;

import com.zero.fastlms.admin.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> list();

    boolean add(String categoryName);

    boolean update(CategoryDto parameter);

    boolean delete(Long id);
}
