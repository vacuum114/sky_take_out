package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    @Delete("DELETE FROM dish WHERE category_id=#{categoryId}")
    void deleteByCategoryId(Long categoryId);
    @Select("SELECT count(*) FROM dish WHERE category_id=#{categoryId}")
    int getCountByCategoryId(Long categoryId);
    @AutoFill(value=OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pagequery(DishPageQueryDTO dto);
    @Select("SELECT * FROM dish WHERE id=#{id}")
    Dish getById(Long id);
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    List<Dish> getByCategoryId(Dish dish);

    void deleteByIds(List<Long>ids);
}
