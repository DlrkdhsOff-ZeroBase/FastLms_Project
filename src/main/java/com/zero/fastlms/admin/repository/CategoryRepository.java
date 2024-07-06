package com.zero.fastlms.admin.repository;

import com.zero.fastlms.admin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
