package com.example.shop.repository;

import com.example.shop.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

@Mapper
public interface UserRepository {
    @Select("select * from users where email= #{email}")
    Optional<User> findByEmail(String email);

    @Insert("insert into users (user_name, email, phone, password, authority, default_address_id) " +
            "values (#{username}, #{email}, #{phone}, #{password}, 'USER', #{defaultAddressId})")
    void insertUser(String username, String email, String phone, String password, Long defaultAddressId);

    @Update("update users set user_name= #{username}, email= #{email}, phone= #{phone}" +
            "where user_id= #{userId}")
    void updateUserInfo(Long userId, String username, String email, String phone);

    @Update("update users set password= #{password} where user_id= #{userId}")
    void updatePassword(Long userId, String password);

    @Update("update users set default_address_id= #{defaultAddressId} " +
            "where user_id= #{userId}")
    void updateDefaultAddress(Long userId, Long defaultAddressId);
}
