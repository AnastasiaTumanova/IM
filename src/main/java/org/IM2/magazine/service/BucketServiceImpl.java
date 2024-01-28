package org.IM2.magazine.service;

import org.IM2.magazine.Enum.StatusEnum;
import org.IM2.magazine.dao.BucketRepository;
import org.IM2.magazine.dao.ProductRepository;
import org.IM2.magazine.dto.BucketDTO;
import org.IM2.magazine.dto.BucketDetailDTO;
import org.IM2.magazine.models.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements  BucketService{
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, UserService userService) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProductList(productList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream().map(productRepository::getOne).collect(Collectors.toList());
    }

    @Override
    public void addProducts(Bucket bucket, List<Long> productIds) {
            List<Product> products = bucket.getProductList();
            List<Product> newProductList = products==null?new ArrayList<>(): new ArrayList<>(products);
            newProductList.addAll(getCollectRefProductsByIds(productIds));
            bucket.setProductList(newProductList);
            bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);
        if(user == null || user.getBucket() == null){
            return new BucketDTO();
        }

        BucketDTO bucketDto = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProductList();
        for (Product product : products) {
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            if(detail == null){
                mapByProductId.put(product.getId(), new BucketDetailDTO(product));
            }
            else {
                detail.setAmount(detail.getAmount() + 1.0);
                detail.setSum(detail.getSum() + product.getPrice());
            }
        }

        bucketDto.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDto.aggregate();

        return bucketDto;
    }


}
