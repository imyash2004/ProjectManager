package com.yash.service.impl;

import com.yash.models.Issue;
import com.yash.models.Project;
import com.yash.models.User;
import com.yash.repo.IssueRepo;
import com.yash.request.IssueRequest;
import com.yash.service.IssueService;
import com.yash.service.ProjectService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepo issueRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;
    @Override
    public Issue getIssueById(Long issueId) throws Exception {
        Optional<Issue>issue=issueRepo.findById(issueId);
        if(issue.isEmpty()){
            throw new Exception("no issue found");
        }
        return issue.get();
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {

        return issueRepo.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issue, User user) throws Exception {
        Issue createIssue=new Issue();
        Project project=projectService.getProjectById(issue.getPId());
        createIssue.setDescription(issue.getDescription());
        createIssue.setPriority(issue.getPriority());
        createIssue.setStatus(issue.getStatus());
        createIssue.setTitle(issue.getTitle());
        createIssue.setProjectID(issue.getPId());
        createIssue.setDueDate(issue.getDueDate());
        createIssue.setProject(project);
        createIssue.setAssigned(user);

        return issueRepo.save(createIssue);

    }

    @Override
    public void deleteIssue(Long issueId, long userId) throws Exception {
        getIssueById(issueId);
        issueRepo.deleteById(issueId);

    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user=userService.findUserById(userId);
        Issue issue=getIssueById(issueId);
        issue.setAssigned(user);

        return issueRepo.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue=getIssueById(issueId);
        issue.setStatus(status);
        return issueRepo.save(issue);
    }
}
