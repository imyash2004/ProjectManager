package com.yash.service;

import com.yash.models.Issue;
import com.yash.models.User;
import com.yash.request.IssueRequest;

import java.util.List;

public interface IssueService {
    Issue getIssueById(Long issueId)throws Exception;
    List<Issue>getIssueByProjectId(Long projectId)throws Exception;

    Issue createIssue(IssueRequest issue, User user)throws Exception;

//    Optional<Issue>updateIssue(Long issueId,IssueRequest req,Long userId)throws Exception;

    void deleteIssue(Long issueId, long userId)throws Exception;



//    List<Issue>getIssueByAssigneeId(Long assigneeId)throws Exception;
//    List<Issue>searchIssues(String title,String status,String priority,Long assigneeId)throws Exception;
//    List<User>getAssigneeForIssue(Long issueId)throws Exception;
    Issue addUserToIssue(Long issueId,Long userId)throws Exception;

    Issue updateStatus(Long issueId,String status)throws Exception;




}

