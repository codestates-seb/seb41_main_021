package com.yata.backend.domain.yata.service;

public interface YataInviteService {
    void acceptInvitation(String username, Long yataRequestId);
    void rejectInvitation(String username, Long yataRequestId);
}
