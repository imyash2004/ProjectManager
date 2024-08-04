package com.yash.Controller;

import com.yash.models.Chat;
import com.yash.models.Invitation;
import com.yash.models.Project;
import com.yash.models.User;
import com.yash.request.InvitationRequest;
import com.yash.response.MessageResponse;
import com.yash.service.InvitationService;
import com.yash.service.ProjectService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;


    @Autowired
    private InvitationService invitationService;
    @GetMapping("/")
    public ResponseEntity<List<Project>>getProjects(@RequestParam(required = false)String category,
                                                    @RequestParam(required = false)String tags,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Project>projects=projectService.getProjectByTeam(user,category,tags);


        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @GetMapping("/{projectId}")
    public ResponseEntity<Project>getProjectById(@PathVariable Long projectId,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Project project=projectService.getProjectById(projectId);


        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Project>createProject(@RequestBody Project project,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Project createdProject=projectService.createProject(project,user);


        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }
    @PatchMapping("/{projectId}")
    public ResponseEntity<Project>updateProject(@PathVariable Long projectId,
                                                @RequestBody Project project,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Project updatedProject=projectService.updateProject(project,projectId);


        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> updateProject(@PathVariable Long projectId,

                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse res=new MessageResponse();
        res.setMessage("project deletion completed");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }




    @GetMapping("/search")
    public ResponseEntity<List<Project>>searchProjects(@RequestParam(required = false)String keyword,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Project>projects=projectService.searchProject(keyword,user);


        return new ResponseEntity<>(projects, HttpStatus.OK);
    }




    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat>getChatByProjectId(@PathVariable Long id,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Project project=projectService.getProjectById(id);

        Chat chat=projectService.getChatByProjectId(project.getId());


        return new ResponseEntity<>(chat, HttpStatus.OK);
    }





    @PostMapping("/invite")
    public ResponseEntity<MessageResponse>inviteProject(@RequestBody InvitationRequest req,
                                                        @RequestHeader("Authorization") String jwt,
                                                        @RequestBody Project project) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(req.getEmail(),req.getProjectId());

        MessageResponse res=new MessageResponse("user invitation sent successfully");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation>acceptProject(@RequestParam String token
            ,
                                                        @RequestHeader("Authorization") String jwt,
                                                        @RequestBody Project project) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Invitation invitation=invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId() );

        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }




}
