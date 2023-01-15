package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.mapper.YataMapper;
import com.yata.backend.domain.yata.service.YataService;
import com.yata.backend.global.response.SingleResponse;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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

    //게시물 생성
    @PostMapping
    public ResponseEntity postNeota(@Valid @RequestBody YataDto.YataPost requestBody,
                                    @RequestParam String yataStatus,
                                    @AuthenticationPrincipal User authMember) throws ParseException {

        Yata yata = yataService.createYata(mapper.yataPostDtoToYata(requestBody),yataStatus,authMember.getUsername());
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataToYataResponse(yata)), HttpStatus.CREATED);
    }

    @PatchMapping("/{yata_id}")
    public ResponseEntity patchYata(@PathVariable("yata_id") @Positive long yataId,
                                     @Valid @RequestBody YataDto.Patch requestbody,
                                    @AuthenticationPrincipal User authMember){
        Yata yata = this.yataService.updateYata(yataId,this.mapper.yataPatchToYata(requestbody),authMember.getUsername());
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataToYataResponse(yata)),HttpStatus.OK);
    }

    @DeleteMapping("/{yata_id}")
    public ResponseEntity deleteNeota(@PathVariable("yata_id") @Positive long yataId,
                                      @AuthenticationPrincipal User authMember){

        this.yataService.deleteYata(yataId,authMember.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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