package com.yata.backend.domain.notify.aop.proxy;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.notify.entity.Notify;

public interface NotifyInfo {
    Member getReceiver();
    Long getGoUrlId();
    Notify.NotificationType getNotificationType();
}
