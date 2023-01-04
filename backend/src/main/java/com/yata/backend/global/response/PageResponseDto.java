package com.yata.backend.global.response;

import com.yata.backend.global.response.PageInfo;
import lombok.*;
import org.springframework.data.domain.Pageable;

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
