package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface YataMapper {
    Yata neotaPostDtoToYata(YataDto.NeotaPost requestBody);
    Yata nataPostDtoToYata(YataDto.NataPost requestBody);
    Yata yataPatchToYata(YataDto.Patch requestBody);
    YataDto.Response yataToYataResponse(Yata yata);
    List<YataDto.Response> yatasToYataResponses(List<Yata> yatas);

}
