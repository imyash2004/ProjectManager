package com.yash.repo;

import com.yash.models.Project;
import com.yash.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project,Long> {

    List<Project>findByOwner(User user);
//    @Query("Select p from Project p join p.team t where t=:user")
//    List<Project>findByTeam(@Param("user")User user);
    List<Project>findByNameContainingAndTeamContainsIgnoreCase(String partialName,User user);

    List<Project>findByTeamContainingOrOwner(User user,User owner);


}
