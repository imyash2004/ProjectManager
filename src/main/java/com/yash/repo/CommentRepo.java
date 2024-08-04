package com.yash.repo;

import com.yash.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comments,Long> {
    List<Comments> findCommentsByIssueId(Long issueId);


}
