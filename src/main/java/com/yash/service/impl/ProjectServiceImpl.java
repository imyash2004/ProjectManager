package com.yash.service.impl;

import com.yash.models.Chat;
import com.yash.models.Project;
import com.yash.models.User;
import com.yash.repo.ProjectRepo;
import com.yash.service.ChatService;
import com.yash.service.ProjectService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;


    @Override
    public Project createProject(Project project, User user) throws Exception {

        Project project1=new Project();
        project1.setName(project.getName());
        project1.setCategory(project.getCategory());
        project1.setDescription(project.getDescription());
        project1.setTags(project.getTags());
        project1.setOwner(user);
        project1.getTeam().add(user);

        Project savedProject=projectRepo.save(project1);
        Chat chat=new Chat();
        chat.setProject(savedProject);
        Chat savedChat=chatService.createChat(chat);
        savedProject.setChat(savedChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {


        List<Project>projects=projectRepo.findByTeamContainingOrOwner(user,user);
        if(category!=null){
            projects=projects.stream().filter(project -> project.getCategory().equals(category)).collect(Collectors.toList());
        }
        if(tag!=null){
            projects=projects.stream().filter(project -> project.getTags().contains(tag)).collect(Collectors.toList());

        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project>project=projectRepo.findById(projectId);
        if(project.isEmpty())throw new Exception("project not found");

        return project.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        getProjectById(projectId);
        projectRepo.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project =getProjectById(id);
        project.setTags(updatedProject.getTags());
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return projectRepo.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project=getProjectById(projectId);
        User user=userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);

        }
        projectRepo.save(project);
    }

    @Override
    public void removeUserToProject(Long projectId, Long userId) throws Exception {
        Project project=getProjectById(projectId);
        User user=userService.findUserById(userId);
        if(project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);

        }
        projectRepo.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project=getProjectById(projectId);


        return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) throws Exception{
        String partialName="%"+keyword+"%";
        List<Project>projects=projectRepo.findByNameContainingAndTeamContainsIgnoreCase(partialName,user);
        return projects;
    }

}
