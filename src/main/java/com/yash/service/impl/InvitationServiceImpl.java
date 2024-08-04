package com.yash.service.impl;

import com.yash.models.Invitation;
import com.yash.repo.InvitationRepo;
import com.yash.service.EmailService;
import com.yash.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {


    @Autowired
    private InvitationRepo invitationRepo;

    @Autowired
    private EmailService emailService;
    @Override
    public void sendInvitation(String email, Long projectId) {
        String invitationToken= UUID.randomUUID().toString();
        Invitation invitation=new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);
        invitationRepo.save(invitation);
        String invitationLinks="";
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {

        Invitation invitation=invitationRepo.findByToken(token);
        if(invitation==null){
            throw new Exception("Invalid invitation token");
        }



        return invitation;
    }

    @Override
    public String getTokenByUserMail(String email) {
        Invitation invitation=invitationRepo.findByEmail(email);


        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {

        Invitation invitation=invitationRepo.findByToken(token);
        invitationRepo.delete(invitation);

    }
}
