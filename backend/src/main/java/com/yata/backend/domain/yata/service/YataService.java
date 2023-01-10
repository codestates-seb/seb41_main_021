package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.stereotype.Service;

public interface YataService {
    Yata createNeota(Yata yata);

    Yata createNata(Yata yata);

    Yata updateNeota(long yataId,Yata yata);

    Yata deleteNeota();

    Yata findAllNeota();

    Yata findNeota();

}
