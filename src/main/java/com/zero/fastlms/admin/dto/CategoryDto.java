package com.zero.fastlms.admin.dto;

import com.zero.fastlms.admin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    String categoryName;
    int sortValue;

    boolean usingYn;

    int courseCount;

    public static List<CategoryDto> of(List<Category> categories) {
        if (!categories.isEmpty()) {
            List<CategoryDto> categoryList = new ArrayList<>();
            for (Category category : categories) {
                categoryList.add(of(category));
            }
            return categoryList;
        }

        return null;
    }

    public static CategoryDto of(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .sortValue(category.getSortValue())
                .usingYn(category.isUsingYn())
                .build();
    }
}
