package com.learn.ad.service;

import com.learn.ad.exception.AdException;
import com.learn.ad.vo.CreateUserRequest;
import com.learn.ad.vo.CreateUserResponse;

public interface IUserService {
    CreateUserResponse createUser(CreateUserRequest request)throws AdException;
}
