package com.yata.backend.domain.review.service;

import com.yata.backend.domain.review.entity.Review;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    public Review createReview(Review review, String username, long yataId){
        return null;
    }
}
