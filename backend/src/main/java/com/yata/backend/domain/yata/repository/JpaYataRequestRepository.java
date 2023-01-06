package com.yata.backend.domain.yata.repository;

import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaYataRequestRepository extends JpaRepository<YataRequest, Long>, YataRequestRepository{
}
