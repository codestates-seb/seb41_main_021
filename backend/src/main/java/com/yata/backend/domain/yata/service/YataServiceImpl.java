package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import com.yata.backend.domain.yata.repository.yataRepo.YataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class YataServiceImpl implements YataService{

    JpaYataRepository jpayataRepository;

    public YataServiceImpl(JpaYataRepository jpayataRepository){
        this.jpayataRepository = jpayataRepository;
    }

    @Override
    public Yata createNeota(Yata yata) {
        return jpayataRepository.save(yata);
    }

    @Override
    public Yata createNata(Yata yata) {
        return jpayataRepository.save(yata);
    }

    @Override
    public Yata updateNeota() {
        return null;
    }

    @Override
    public Yata deleteNeota() {
        return null;
    }

    @Override
    public Yata findAllNeota() {
        return null;
    }

    @Override
    public Yata findNeota() {
        return null;
    }

}
