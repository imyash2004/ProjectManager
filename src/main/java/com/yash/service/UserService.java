package com.yash.service;

import com.yash.models.User;

public interface UserService {

    User findUserProfileByJwt(String jwt)throws Exception;

    User findUserById(Long userId)throws Exception;

    User findUserByEmail(String email)throws Exception;

    User updateUserProjectSize(User user ,int number);
}
