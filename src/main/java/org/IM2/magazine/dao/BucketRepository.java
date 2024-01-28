package org.IM2.magazine.dao;

import org.IM2.magazine.models.Bucket;
import org.IM2.magazine.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
    @Query(nativeQuery = true, value="SELECT * FROM product WHERE product.title LIKE %?1%")
    Iterable<Product> findAll(String keyword);
}
