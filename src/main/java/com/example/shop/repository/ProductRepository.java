package com.example.shop.repository;

import com.example.shop.model.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductRepository {
    @Select("select * from products")
    List<Product> findAll();

    @Select("select * from products where product_name like '%'|| #{keyword} ||'%' ")
    List<Product> findByKeyword(String keyword);

    @Select("select * from products where product_id = #{productId}")
    Product findById(Long productId);

    @Update("update products set stock= #{stock} where product_id= #{productId}")
    void updateProductStock(Long productId, Integer stock)
            ;
//    @Insert("insert into users (user_name, email, phone, password, authority) " +
//            "values (#{username}, #{email}, #{phone}, #{password}, 'USER')")
//    void insert(String username, String email, String phone, String password);
}
