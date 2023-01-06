package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.repository.yataRepo.YataRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class YataServiceImpl {

    YataRepository yataRepository;

    public YataServiceImpl(YataRepository yataRepository){
        this.yataRepository =yataRepository;
    }
    Yata createNeota(Yata yata){
        //
        return yataRepository.save();
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
