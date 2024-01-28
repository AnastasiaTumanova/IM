package org.IM2.magazine.service;

import org.IM2.magazine.dao.ProductRepository;
import org.IM2.magazine.dto.ProductDTO;
import org.IM2.magazine.mapper.ProductMapper;
import org.IM2.magazine.models.Bucket;
import org.IM2.magazine.models.Product;
import org.IM2.magazine.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }



    @Override
    @Transactional
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if(user == null){
            throw new RuntimeException("Пользователь с именем "+username + "не найден");
        }
        Bucket bucket = user.getBucket();
        if(bucket==null){
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);

        }
        else{
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }

    @Override
    public ProductDTO getById(Long id) {
        Product product = productRepository.findById(id).orElse(new Product());
        return ProductMapper.MAPPER.fromProduct(product);
    }

    @Override
    @Transactional
    public void addProduct(ProductDTO dto) {
        Product product = ProductMapper.MAPPER.toProduct(dto);
        Product savedProduct = productRepository.save(product);
        ProductMapper.MAPPER.fromProduct(savedProduct);
    }
}
