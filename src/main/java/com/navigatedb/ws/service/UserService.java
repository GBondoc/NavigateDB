package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.UserDto;

import java.util.List;


public interface UserService {
    UserDto createUser(UserDto user);
    UserDto getUserByUserId(String userId);
    UserDto updateUser(String userId, UserDto user);
    void deleteUser(String userId);
    List<UserDto> getUsers(int page, int limit);
}
