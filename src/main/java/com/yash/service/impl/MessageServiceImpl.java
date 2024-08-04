package com.yash.service.impl;

import com.yash.models.Chat;
import com.yash.models.Message;
import com.yash.models.User;
import com.yash.repo.MessageRepo;
import com.yash.repo.UserRepo;
import com.yash.service.MessageService;
import com.yash.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender=userRepo.findById(senderId).orElseThrow(()->new Exception("user not found...."));


        Chat chat=projectService.getProjectById(projectId).getChat();

        Message message=new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedDate(LocalDate.now());
        message.setChat(chat);
        Message saved=messageRepo.save(message);

        chat.getMessages().add(saved);
        return saved;

    }

    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat=projectService.getChatByProjectId(projectId);
        List<Message>messages=messageRepo.findByChatIdOrderByCreatedDateAsc(chat.getId());
        return messages;
    }
}
