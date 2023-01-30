package com.yata.backend.global.aop.proxy;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.notify.entity.Notify;

public interface NotifyAdapter {
    Member getReceiver();
    Long getGoUrlId();
    Notify.NotificationType getNotificationType();
}
