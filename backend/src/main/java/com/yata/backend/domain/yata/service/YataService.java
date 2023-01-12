package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.stereotype.Service;

public interface YataService {
    Yata createYata(Yata yata,String yataStatus, String userName);

    Yata updateYata(long yataId,Yata yata,String userName);

    void deleteYata(long yataId,String username);

    Yata findAllYata();

    Yata findYata();

    Yata verifyYata(long yataId);


}
