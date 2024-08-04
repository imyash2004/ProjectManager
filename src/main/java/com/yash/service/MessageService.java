package com.yash.service;

import com.yash.models.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId,Long chatId,String content)throws Exception;
    List<Message>getMessageByProjectId(Long projectId)throws Exception;

}
