package com.yata.backend.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class DriverAuthDto {
    private String name;
    // 영어 숫자 12자리
    @Pattern(regexp = "^[a-zA-Z0-9]{12}$")
    private String driverLicenseNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dateOfIssue;
}
