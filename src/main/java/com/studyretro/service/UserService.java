package com.studyretro.service;

import com.studyretro.entity.Users;

import java.util.List;

public interface UserService {

    public Users registerUser(Users users);

    public List<?> getUsers(Users users);

    public Users updateUsers(Users users);

    public boolean emailExist(String email);

    public boolean phoneExist(String phone);

}
