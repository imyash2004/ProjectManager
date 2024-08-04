package com.yash.Controller;

import com.yash.models.Issue;
import com.yash.models.User;
import com.yash.request.IssueDto;
import com.yash.request.IssueRequest;
import com.yash.response.AuthResponse;
import com.yash.response.MessageResponse;
import com.yash.service.IssueService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue>getIssueById(@PathVariable Long issueId,
                                             @RequestHeader("Authorization") String jwt)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Issue issue=issueService.getIssueById(issueId);
        return ResponseEntity.ok(issue);

    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>>getIssueByProjectId(@PathVariable Long projectId,
                                                          @RequestHeader("Authorization") String jwt)throws Exception{
        List<Issue>issues=issueService.getIssueByProjectId(projectId);

        return ResponseEntity.ok(issues);

    }

    @PostMapping
    public ResponseEntity<IssueDto>createIssue(@RequestHeader("Authorization") String jwt,
                                               @RequestBody IssueRequest req)throws Exception{
        User tokenUser=userService.findUserProfileByJwt(jwt);
        User user=userService.findUserById(tokenUser.getId());



            Issue createIssue=issueService.createIssue(req,user);
            IssueDto issueDto=new IssueDto();
            issueDto.setDescription(createIssue.getDescription());
            issueDto.setTitle(createIssue.getTitle());
            issueDto.setAssignee(createIssue.getAssigned());
            issueDto.setDueDate(createIssue.getDueDate());
            issueDto.setId(createIssue.getId());
            issueDto.setPriority(createIssue.getPriority());
            issueDto.setStatus(createIssue.getStatus());
            issueDto.setTags(createIssue.getTags());
            issueDto.setProjectId(createIssue.getProjectID());
            issueDto.setProject(createIssue.getProject());
            return ResponseEntity.ok(issueDto);

    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse>deleteIssue(@PathVariable Long issueId,

                                                   @RequestHeader("Authorization") String jwt)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);

        issueService.deleteIssue(issueId,user.getId());
        MessageResponse res=new MessageResponse("issue deleted successfully");

        return ResponseEntity.ok(res);


    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue>addUserToIssue(@PathVariable Long issueId,
                                               @PathVariable Long userId,
                                               @RequestHeader("Authorization") String jwt)throws Exception{
//        Issue issue=issueService.getIssueById(issueId);
        Issue issue=issueService.addUserToIssue(issueId,userId);

        return ResponseEntity.ok(issue);
    }
    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue>updateIssueStatus(@PathVariable Long issueId,
                                               @PathVariable String status,
                                               @RequestHeader("Authorization") String jwt)throws Exception{
//        Issue issue=issueService.getIssueById(issueId);
        Issue issue=issueService.updateStatus(issueId,status);

        return ResponseEntity.ok(issue);
    }

}
