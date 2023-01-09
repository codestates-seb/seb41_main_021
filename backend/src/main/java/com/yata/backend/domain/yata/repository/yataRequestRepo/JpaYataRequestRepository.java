package com.yata.backend.domain.yata.repository.yataRequestRepo;

import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaYataRequestRepository extends JpaRepository<YataRequest, Long>, YataRequestRepository {
    Page<YataRequest> findAllByRequestStatus(Pageable pageable, YataRequest requestStatus); // TODO 쿼리로 status 가 ~~인 애들만 가져오기

    List<YataRequest> findByRequest(YataRequest yataRequest);
}
