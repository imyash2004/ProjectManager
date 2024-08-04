package com.yash.service;

import com.yash.models.Invitation;

public interface InvitationService {
    void sendInvitation(String email,Long projectId);
    Invitation acceptInvitation(String token,Long userId) throws Exception;
    String getTokenByUserMail(String email);

    void deleteToken(String token);
}
