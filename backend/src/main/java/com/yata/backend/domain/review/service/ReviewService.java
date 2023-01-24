package com.yata.backend.domain.review.service;

import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import io.netty.channel.pool.ChannelHealthChecker;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface ReviewService {
    Review createReview(List<Long> checklistIds, String username, long yataId,Long yataMemberId);

    Map<Checklist,Long> findAllReview(String userName);

}
