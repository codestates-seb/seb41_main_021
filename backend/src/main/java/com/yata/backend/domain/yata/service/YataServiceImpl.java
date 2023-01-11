package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import com.yata.backend.domain.yata.repository.yataRepo.YataRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.yata.backend.domain.yata.entity.Yata.PostStatus.POST_WAITING;

@Service
@Transactional
public class YataServiceImpl implements YataService{

    JpaYataRepository jpayataRepository;

    public YataServiceImpl(JpaYataRepository jpayataRepository){
        this.jpayataRepository = jpayataRepository;
    }

    @Override
    public Yata createYata(Yata yata,String yataStatus, String userName) {
        //이메일로 멤버 찾고
        //yata에 멤버 추가해줌 addmember
        switch (yataStatus){
            case "neota" -> yata.setYataStatus(YataStatus.YATA_NEOTA);
            case "nata" -> yata.setYataStatus(YataStatus.YATA_NATA);
            default ->  throw new CustomLogicException(ExceptionCode.YATA_STATUS_NONE);
        }

        return jpayataRepository.save(yata);
    }


    @Override
    public Yata updateNeota(long yataId,Yata yata) {

//        //존재하는 게시물인지 확인
//        Yata findYata = verifyYata(yataId);
//
//        //예완 상태 아닌 게시물인지 확인
//        //예안 게시물이면 -> 예외
//        modifiableYata(yataId);
//
//        //수정 내용이 있다면 바꿔서 저장해주기
//        //todo 출발지,도착지,체크리스트,...
//        Optional.ofNullable(yata.getAmount())
//                .ifPresent(amount -> findYata.setAmount(amount));
//        Optional.ofNullable(yata.getMaxPeople())
//                .ifPresent(n -> findYata.setMaxPeople(n));
////        Optional.ofNullable(yata.getDepartureTime())
////                .ifPresent(n -> findYata.setDepartureTime(n));
////        Optional.ofNullable(yata.getTimeOfArrival())
////                .ifPresent(n -> findYata.setTimeOfArrival(n));
//        Optional.ofNullable(yata.getCarModel())
//                .ifPresent(n -> findYata.setCarModel(n));
//
//    return jpayataRepository.save(findYata);
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
