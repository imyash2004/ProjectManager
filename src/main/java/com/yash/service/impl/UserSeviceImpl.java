package com.yash.service.impl;

import com.yash.config.JwtProvider;
import com.yash.models.User;
import com.yash.repo.UserRepo;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSeviceImpl implements UserService {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepo userRepo;
    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromToken(jwt);
        User user=userRepo.findByEmail(email);
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User >user=userRepo.findById(userId);
        if(user==null){
            throw new Exception("user not found ....");
        }
        return user.get();
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=userRepo.findByEmail(email);
        if(user==null){
            throw new Exception("user not found ....");
        }
        return user;
    }

    @Override
    public User updateUserProjectSize(User user, int number) {

        user.setProjectSize(user.getProjectSize()+number);

        return userRepo.save(user);
    }
}
