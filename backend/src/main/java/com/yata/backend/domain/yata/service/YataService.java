package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

public interface YataService {
    Yata createYata(Yata yata, String userName);

    Yata updateYata(long yataId,Yata yata,String userName);

    void deleteYata(long yataId,String username);

    Slice<Yata> findAllYata(String yataStatus, Pageable pageable);

    Yata findYata(long yataId);

    Slice<Yata> findMyYata(String userName, Pageable pageable);

    void equalMember(String email, String postEmail);


}
