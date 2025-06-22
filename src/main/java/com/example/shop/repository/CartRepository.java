package com.example.shop.repository;

import com.example.shop.model.entity.Cart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CartRepository {
    @Select("select * from carts where user_id= #{userId}")
    Cart findByUserId(Long userId);

    @Insert("insert into carts (user_id) values (#{userId})")
    void insertCart(Long userId);
}
