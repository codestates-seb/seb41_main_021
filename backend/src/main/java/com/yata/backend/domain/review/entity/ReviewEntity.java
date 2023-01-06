package com.yata.backend.domain.review.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReviewEntity {
    @Id
    private Long reviewId;
}
