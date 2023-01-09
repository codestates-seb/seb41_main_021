package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.mapper.YataMapper;
import com.yata.backend.domain.yata.service.YataService;
import com.yata.backend.domain.yata.service.YataServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/v1/yata")
public class YataController {

    private YataService yataService;
    private YataMapper mapper;

    public YataController(YataService yataService, YataMapper mapper){

        this.yataService = yataService;
        this.mapper=mapper;
    }

    //너타생성
    @PostMapping("/neota")
    public ResponseEntity postNeota(@Valid @RequestBody YataDto.NeotaPost requestBody){
        Yata yata = yataService.createNeota(mapper.neotaPostDtoToYata(requestBody));
        return new ResponseEntity<>((mapper.yataToYataResponse(yata)), HttpStatus.OK);
    }

    //나타생성
    @PostMapping("/nata")
    public ResponseEntity postNata(@Valid @RequestBody YataDto.NataPost requestBody){
        Yata yata = yataService.createNata(mapper.nataPostDtoToYata(requestBody));
        return new ResponseEntity<>((mapper.yataToYataResponse(yata)), HttpStatus.OK);
    }

    @PatchMapping("/{yata_id}")
    public ResponseEntity patchNeota(){
        return null;
    }

    @DeleteMapping("/{yata_id}")
    public ResponseEntity deleteNeota(){
        return null;
    }

    //너타목록 불러오기
    @GetMapping("/neota")
    public ResponseEntity getAllNeota(){
        return null;
    }

    //나타목록 불러오기
    @GetMapping("/nata")
    public ResponseEntity getAllNata(){
        return null;
    }


    //상세보기
    @GetMapping("/{yata_id}")
    public ResponseEntity getYata(){
        return null;
    }

}