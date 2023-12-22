package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);

    PageResult pagequery(DishPageQueryDTO dto);

    DishDTO getById(Long id);

    void editDish(DishDTO dto);

    List<Dish> getByCategoryId(Long categoryId);

    void setStatus(Integer status,long id);

    void delele(List<Long>ids);

    List<DishVO> listWithFlavor(Dish dish);
}
