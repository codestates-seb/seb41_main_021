package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.service.YataService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/v1/yata")
public class YataController {

    private YataService yataService;

    public YataController(YataService yataService){
        this.yataService = yataService;
    }

    //너타생성
    @PostMapping("/neota")
    public ResponseEntity postNeota(@Valid @RequestBody YataDto.NeotaPost neotaPost){
        Yata yata = yataService.createNeota();

    }

    //나타생성
    @PostMapping("/nata")
    public ResponseEntity postNata(){
        return null;
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