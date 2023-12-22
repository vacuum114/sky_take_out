package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @Delete("DELETE FROM setmeal WHERE category_id=#{categoryId}")
    void deleteByCategoryId(Long categoryId);
    @Select("SELECT count(*) FROM setmeal WHERE category_id=#{categoryId}")
    int getCountByCategoryId(Long categoryId);

    Page<SetmealVO> pagequery(SetmealPageQueryDTO pageQueryDTO);
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
    @Select("SELECT * FROM setmeal WHERE id=#{id}")
    Setmeal getById(long id);
    void delete(List<Long> ids);
    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);
}
