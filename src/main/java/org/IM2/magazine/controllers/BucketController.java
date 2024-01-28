package org.IM2.magazine.controllers;

import org.IM2.magazine.dao.BucketRepository;
import org.IM2.magazine.dto.BucketDTO;
import org.IM2.magazine.dto.BucketDetailDTO;
import org.IM2.magazine.models.Bucket;
import org.IM2.magazine.service.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class BucketController {
    private final BucketService bucketService;
    private final BucketRepository bucketRepository;

    public BucketController(BucketService bucketService, BucketRepository bucketRepository) {
        this.bucketService = bucketService;
        this.bucketRepository = bucketRepository;
    }
    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal){
        if(principal ==null){
            model.addAttribute("bucket", new BucketDTO());
        }
        else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        }
        return "bucket";
    }

}
