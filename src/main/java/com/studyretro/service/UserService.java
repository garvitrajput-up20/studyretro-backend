package com.studyretro.service;

import com.studyretro.dto.LoginDto;
import com.studyretro.entity.Users;

import java.util.List;

public interface UserService {

    public Users registerUser(Users users);

    public List<?> getUsers(Users users);

    public Users updateUsers(Users users);

    public String delete();

    public boolean emailExist(String email);

    public boolean phoneExist(String phone);

    String login(LoginDto loginDto);
}
