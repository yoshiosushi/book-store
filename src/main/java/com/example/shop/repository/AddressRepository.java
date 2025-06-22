package com.example.shop.repository;

import com.example.shop.model.entity.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AddressRepository {
    @Select("select * from addresses where user_id= #{useId}")
    List<Address> findByUserId(Long userId);

    @Select("select * from addresses where address_id= #{addressId}")
    Optional<Address> findByAddressId(Long addressId);

    @Insert("insert into addresses (user_id, postal_code_f, postal_code_b, forward_address, last_name, first_name, phone) " +
            "values (#{userId}, #{postalCodeF}, #{postalCodeB}, #{forwardAddress}, " +
            "#{lastName}, #{firstName}, #{phone})")
    void insertAddress(Long userId, String postalCodeF, String postalCodeB, String forwardAddress,
                        String lastName, String firstName, String phone);

    @Update("update addresses set postal_code_f= #{postalCodeF}, postal_code_b= #{postalCodeB}, " +
            "forward_address= #{forwardAddress}, last_name= #{lastName}, first_name= #{firstName}, phone= #{phone}" +
            " where address_id= #{addressId}")
    void updateAddress(Long addressId, String postalCodeF, String postalCodeB, String forwardAddress,
                             String lastName, String firstName, String phone);

    @Delete("delete from addresses where address_id= #{addressId}")
    void deleteAddress(Long addressId);
}
