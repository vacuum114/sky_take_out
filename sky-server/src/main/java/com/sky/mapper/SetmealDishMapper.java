package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    void insertBatch(List<SetmealDish> list);
    @Select("SELECT * FROM setmeal_dish WHERE setmeal_id=#{setmealId}")
    List<SetmealDish> getBySealId(long setmealId);
    void delete(List<Long> setmealIds);
}
