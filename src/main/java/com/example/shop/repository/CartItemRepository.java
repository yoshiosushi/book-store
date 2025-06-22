package com.example.shop.repository;

import com.example.shop.model.entity.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartItemRepository {
    @Select("select * from cart_items where cart_id= #{cartId}")
    List<CartItem> findByCartId(Long cartId);

    @Select("select * from cart_items where cart_id= #{cartId} and product_id= #{productId}")
    Optional<CartItem> findByProductId(Long cartId, Long productId);

    @Insert("insert into cart_items (cart_id, product_id, quantity) " +
            "values (#{cartId}, #{productId}, #{quantity})")
    void insertCartItem(Long cartId, Long productId, Integer quantity);

    @Update("update cart_items set quantity= (quantity + #{quantity}) where cart_item_id= #{cartItemId}")
    void addCartItemQuantity(Long cartItemId, Integer quantity);

    @Update("update cart_items set quantity= #{quantity} where cart_item_id= #{cartItemId}")
    void updateCartItemQuantity(Long cartItemId, Integer quantity);

    @Delete("delete from cart_items where cart_item_id= #{cartItemId}")
    void deleteCartItem(Long cartItemId);
}
