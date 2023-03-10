package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

public interface YataService {
    Yata createYata(Yata yata, String userName);

    Yata updateYata(long yataId,Yata yata,String userName);

    void deleteYata(long yataId,String username);

    Slice<Yata> findAllYata(String yataStatus, Pageable pageable);

    Yata findYata(long yataId);

    Slice<Yata> findMyAcceptedYata(String userName, Pageable pageable);
    Slice<Yata> findMyRequestedYatas(String userName, Pageable pageable);

    Slice<Yata> findMyYatas(String userName, Pageable pageable , String yataStatus , Boolean isExpired);

    void equalMember(String email, String postEmail);


    List<Yata> findMyYatasNotClosed(String username);

}
