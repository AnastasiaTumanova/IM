package org.IM2.magazine.service;

import org.IM2.magazine.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserBucket(Long productId, String username);

    ProductDTO getById(Long id);

    void addProduct(ProductDTO dto);

}
