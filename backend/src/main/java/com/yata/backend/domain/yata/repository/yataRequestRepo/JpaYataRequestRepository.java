package com.yata.backend.domain.yata.repository.yataRequestRepo;

import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaYataRequestRepository extends JpaRepository<YataRequest, Long>, YataRequestRepository {
    Optional<YataRequest> findByMember_EmailAndYata_YataId(String email, Long yataId);
    Slice<YataRequest> findAllByYata(Yata yata, Pageable pageable);
}
