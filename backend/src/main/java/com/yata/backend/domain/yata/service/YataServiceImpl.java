package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import com.yata.backend.domain.yata.repository.yataRepo.YataRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import com.yata.backend.global.utils.CustomBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.yata.backend.domain.yata.entity.Yata.PostStatus.POST_WAITING;

@Service
@Transactional
public class YataServiceImpl implements YataService{

    private final JpaYataRepository jpayataRepository;
    private final CustomBeanUtils<Yata> beanUtils;


    public YataServiceImpl(JpaYataRepository jpayataRepository,CustomBeanUtils<Yata> beanUtils){
        this.jpayataRepository = jpayataRepository;
        this.beanUtils = beanUtils;
    }

    @Override
    public Yata createYata(Yata yata,String yataStatus, String userName) {

        switch (yataStatus){
            case "neota" -> yata.setYataStatus(YataStatus.YATA_NEOTA);
            case "nata" -> yata.setYataStatus(YataStatus.YATA_NATA);
            default ->  throw new CustomLogicException(ExceptionCode.YATA_STATUS_NONE);
        }

        yata.setPostStatus(POST_WAITING);

        return jpayataRepository.save(yata);
    }


    @Override
    public Yata updateYata(long yataId,Yata yata) {

        //존재하는 게시물인지 확인
        Yata findYata = verifyYata(yataId);

        //예완 상태 아닌 게시물인지 확인
        //예안 게시물이면 -> 예외
        modifiableYata(yataId);


        Yata updatingYata = beanUtils.copyNonNullProperties(yata,findYata);

    return jpayataRepository.save(updatingYata);
    }

    @Override
    public Yata deleteYata() {
        return null;
    }

    @Override
    public Yata findAllYata() {
        return null;
    }

    @Override
    public Yata findYata() {
        return null;
    }


    /*검증로직*/
    public Yata verifyYata(long yataId) {
        Optional<Yata> optionalYata = jpayataRepository.findById(yataId);
        Yata findYata = optionalYata.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.YATA_NONE));
        return findYata;
    }

    private void modifiableYata(long yataId){
        Yata findYata = verifyYata(yataId);
        int statusNumber = findYata.getPostStatus().getStatusNumber();
        //예약 전 상태가 아니면 게시물 변경 못하게
        if(statusNumber>1){throw new CustomLogicException(ExceptionCode.YATA_IS_NOT_MODIFIABLE_STATUS);
        }
    }

}
