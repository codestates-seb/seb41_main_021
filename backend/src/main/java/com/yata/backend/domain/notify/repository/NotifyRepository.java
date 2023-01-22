package com.yata.backend.domain.notify.repository;

import com.yata.backend.domain.notify.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, Long> {

}
