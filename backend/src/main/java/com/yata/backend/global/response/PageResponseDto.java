package com.yata.backend.global.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto<T> {
   List<T> data;
   //PageInfo pageInfo;
   PageInfo pageInfo;
}
