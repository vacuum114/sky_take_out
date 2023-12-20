package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    PageResult pagequery(SetmealPageQueryDTO pageQueryDTO);

    void save(SetmealDTO setmealDTO);

    void editSetmeal(SetmealDTO setmealDTO);

    SetmealVO getById(long id);

    void setStatus(Integer status, long id);

    void delete(List<Long> ids);
}
