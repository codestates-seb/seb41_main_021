package com.yata.backend.domain.yata.repository.yataRequestRepo;

import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface YataRequestRepository {
    void updateExpiredYataRequest();
    Slice<YataRequest> findAllByMember_Email(String Email, Pageable pageable);
    Slice<YataRequest> findAllByYata(Yata yata, Pageable pageable);
}
