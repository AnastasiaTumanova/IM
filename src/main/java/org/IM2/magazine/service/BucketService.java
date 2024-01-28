package org.IM2.magazine.service;

import org.IM2.magazine.dto.BucketDTO;
import org.IM2.magazine.models.Bucket;
import org.IM2.magazine.models.User;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);
    void addProducts(Bucket bucket, List<Long> productIds);

    BucketDTO getBucketByUser(String name);


}
