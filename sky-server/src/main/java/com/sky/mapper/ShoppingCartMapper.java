package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 根据id修改购物车内商品数量
     * @param shoppingCart
     */
    @Update("UPDATE shopping_cart SET number=#{number} WHERE id=#{id}")
    void update(ShoppingCart shoppingCart);

    List<ShoppingCart>list(ShoppingCart shoppingCart);
    void insert(ShoppingCart shoppingCart);
    @Delete("DELETE FROM shopping_cart WHERE id=#{id} ")
    void delete(ShoppingCart temp);
    @Delete("DELETE FROM shopping_cart WHERE user_id=#{userId}")
    void deleteAll(Long userId);
}
