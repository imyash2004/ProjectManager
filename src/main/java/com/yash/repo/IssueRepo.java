package com.yash.repo;

import com.yash.models.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepo extends JpaRepository<Issue,Long> {

    List<Issue> findByProjectId(Long projectId);
}
