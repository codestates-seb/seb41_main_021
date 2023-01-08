package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import com.yata.backend.domain.yata.repository.yataRepo.YataRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class YataServiceImpl {

    JpaYataRepository jpayataRepository;

    public YataServiceImpl(JpaYataRepository jpayataRepository){
        this.jpayataRepository = jpayataRepository;
    }
    public Yata createNeota(Yata yata){
        return jpayataRepository.save(yata);
    }

    public Yata createNata(Yata yata){
        return null;
    }

    Yata updateNeota(){
        return null;
    }

    Yata deleteNeota(){
        return null;
    }
    Yata findAllNeota(){
        return null;
    }
    Yata findNeota(){
        return null;
    }

}
