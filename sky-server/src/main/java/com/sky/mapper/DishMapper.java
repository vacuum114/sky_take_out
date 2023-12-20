package com.sky.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    @Delete("DELETE FROM dish WHERE category_id=#{categoryId}")
    void deleteByCategoryId(Long categoryId);
    @Select("SELECT count(*) FROM dish WHERE category_id=#{categoryId}")
    int getCountByCategoryId(Long categoryId);
}
