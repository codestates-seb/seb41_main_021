package com.yata.backend.domain.payHistory.repository;

import com.yata.backend.domain.payHistory.entity.PayHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPayHistoryRepository extends JpaRepository<PayHistory, Long>, PayHistoryRepository {
}
