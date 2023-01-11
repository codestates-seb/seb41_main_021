package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.stereotype.Service;

public interface YataService {
    Yata createYata(Yata yata,String yataStatus, String userName);

    Yata updateNeota(long yataId,Yata yata);

    Yata deleteNeota();

    Yata findAllNeota();

    Yata findNeota();

}
