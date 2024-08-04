package com.yash.service.impl;

import com.yash.models.Comments;
import com.yash.models.Issue;
import com.yash.models.User;
import com.yash.repo.CommentRepo;
import com.yash.repo.IssueRepo;
import com.yash.repo.UserRepo;
import com.yash.service.CommentService;
import com.yash.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private IssueRepo issueRepo;

    @Override
    public Comments createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue>issue=issueRepo.findById(issueId);
        Optional<User>user=userRepo.findById(userId);
        if(issue.isEmpty() || user.isEmpty()){
            throw new Exception("user or issue not present");
        }

        Issue issue1=issue.get();
        User user1=user.get();

        Comments comment=new Comments();
        comment.setContent(content);
        comment.setUser(user1);
        comment.setIssue(issue1);
        comment.setCreatedDate(LocalDate.now());

        Comments created=commentRepo.save(comment);
        issue1.getComments().add(created);
        return created;

    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<User>user=userRepo.findById(userId);
        Optional<Comments>comments=commentRepo.findById(commentId);
        if(comments.isEmpty() || user.isEmpty()){
            throw new Exception("user or comments not present");
        }
        Comments comments1=comments.get();
        User user1=user.get();
        if(comments1.getUser().equals(user1)){
            commentRepo.delete(comments1);
        }
        else throw new Exception("User not found for this comment");



    }

    @Override
    public List<Comments> findCommentByIssueId(Long issueId) throws Exception {
        return commentRepo.findCommentsByIssueId(issueId);
    }
}
