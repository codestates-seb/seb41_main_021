package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;
import org.mapstruct.Mapper;

import java.util.List;

//date to String
//Date from = new Date();
//
//SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//String to = transFormat.format(from);

@Mapper(componentModel = "spring")
public interface YataMapper {
    Yata yataPostDtoToYata(YataDto.YataPost requestBody);
    Yata nataPostDtoToYata(YataDto.YataPost requestBody);
    Yata yataPatchToYata(YataDto.Patch requestBody);
    YataDto.Response yataToYataResponse(Yata yata);

    List<YataDto.Response> yatasToYataResponses(List<Yata> yatas);

}
