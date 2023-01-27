package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.mapper.YataMapper;
import com.yata.backend.domain.yata.service.YataService;
import com.yata.backend.global.response.PageInfo;
import com.yata.backend.global.response.SingleResponse;
import com.yata.backend.global.response.SliceInfo;
import com.yata.backend.global.response.SliceResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.locationtech.jts.io.ParseException;
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

    public YataController(YataService yataService, YataMapper mapper) {

        this.yataService = yataService;
        this.mapper = mapper;
    }

    //게시물 생성
    @PostMapping
    public ResponseEntity postNeota(@Valid @RequestBody YataDto.YataPost requestBody,
                                    @AuthenticationPrincipal User authMember
    ) throws ParseException {

        Yata yata = yataService.createYata(mapper.yataPostDtoToYata(requestBody), authMember.getUsername());
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataToYataResponse(yata)), HttpStatus.CREATED);
    }

    @PatchMapping("/{yata_id}")
    public ResponseEntity patchYata(@PathVariable("yata_id") @Positive long yataId,
                                    @Valid @RequestBody YataDto.Patch requestbody,
                                    @AuthenticationPrincipal User authMember) {
        Yata yata = this.yataService.updateYata(yataId, this.mapper.yataPatchToYata(requestbody), authMember.getUsername());
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataToYataResponse(yata)), HttpStatus.OK);
    }

    @DeleteMapping("/{yata_id}")
    public ResponseEntity deleteNeota(@PathVariable("yata_id") @Positive long yataId,
                                      @AuthenticationPrincipal User authMember) {

        this.yataService.deleteYata(yataId, authMember.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //너타/나타 목록 불러오기
    @GetMapping
    public ResponseEntity getAllYata(@RequestParam String yataStatus, Pageable pageable) {
        Slice<Yata> requests = yataService.findAllYata(yataStatus, pageable);
        SliceInfo sliceInfo = new SliceInfo(pageable, requests.getNumberOfElements(), requests.hasNext());
        return new ResponseEntity<>(
                new SliceResponseDto<>(mapper.yatasToYataResponses(requests.getContent()), sliceInfo), HttpStatus.OK);
    }

    //상세보기
    @GetMapping("/{yata_id}")
    public ResponseEntity getYata(@PathVariable("yata_id") @Positive long yataId) {
        Yata yata = this.yataService.findYata(yataId);
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataToYataResponse(yata)), HttpStatus.OK);

    }

    //내가 쓴 글 조회(신청 온 것만, 마감상태여도 보이게)
    @GetMapping("/my")
    public ResponseEntity getMyYata(@AuthenticationPrincipal User authMember, Pageable pageable) {
        Slice<Yata> requests = yataService.findMyYata(authMember.getUsername(), pageable);
        SliceInfo sliceInfo = new SliceInfo(pageable, requests.getNumberOfElements(), requests.hasNext());
        return new ResponseEntity<>(
                new SliceResponseDto<>(mapper.yatasToYataResponses(requests.getContent()), sliceInfo), HttpStatus.OK);
    }

}