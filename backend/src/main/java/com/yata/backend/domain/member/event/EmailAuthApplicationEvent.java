package com.yata.backend.domain.member.event;

import com.yata.backend.domain.member.dto.EmailAuthDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailAuthApplicationEvent extends ApplicationEvent {
    private EmailAuthDto emailAuthDto;

    public EmailAuthApplicationEvent(Object source, EmailAuthDto emailAuthDto) {
        super(source);
        this.emailAuthDto = emailAuthDto;
    }

}
