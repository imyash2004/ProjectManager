package com.yash.Controller;

import com.yash.models.Chat;
import com.yash.models.Message;
import com.yash.models.User;
import com.yash.request.CreateMessageRequest;
import com.yash.service.MessageService;
import com.yash.service.ProjectService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message>sendMessage(@RequestBody CreateMessageRequest req)throws Exception{
        User user=userService.findUserById(req.getSenderId());
        if (user==null)throw new Exception("user not founddddd");
        Chat chat=projectService.getProjectById(req.getProjectId()).getChat();
        if (chat==null)throw new Exception("chat not found");
        Message sendMessage=messageService.sendMessage(req.getSenderId(), req.getProjectId(), req.getContent());
        return ResponseEntity.ok(sendMessage);

    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>>getMessagesByProjectId(@PathVariable Long projectId)throws Exception{
        List<Message>messages=messageService.getMessageByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
