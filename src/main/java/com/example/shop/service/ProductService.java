package com.example.shop.service;

import com.example.shop.model.entity.CartItem;
import com.example.shop.model.entity.Product;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<Product> findByKeyword(String keyword){
        return productRepository.findByKeyword(keyword);
    }

    public Product findById(Long productId){
        return productRepository.findById(productId);
    }

    public Map<Long, Product> createProductMap(List<CartItem> cartItemList) {
        Map<Long, Product> productMap = new HashMap<>();
        for (CartItem cartItem : cartItemList) {
            Product product = findById(cartItem.getProductId());
            productMap.put(cartItem.getCartItemId(), product);
        }
        return productMap;
    }

    public void updateProductStock(Long productId, Integer quantity){
        Integer stock = findById(productId).getStock();
        productRepository.updateProductStock(productId, (stock - quantity));
    }

}
