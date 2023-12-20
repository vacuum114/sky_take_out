package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    PageResult pagequry(CategoryPageQueryDTO queryDTO);

    void save(CategoryDTO categoryDTO);

    void editcategory(CategoryDTO categoryDTO);

    void setStatus(Integer status,Long id);

    List<Category> getByType(Integer type);

    void deleteById(Long id);
}
