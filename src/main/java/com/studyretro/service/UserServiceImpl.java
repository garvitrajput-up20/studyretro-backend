package com.studyretro.service;

import com.studyretro.dto.LoginDto;
import com.studyretro.entity.Users;
import com.studyretro.exceptions.InvalidUserException;
import com.studyretro.repository.UserRepository;
import com.studyretro.validator.DataValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DataValidator dataValidator;
    private final PasswordEncryptionService encryptionService;

    private final JwtService jwtService;


    @Override
    public Users registerUser(Users users) {
        if(emailExist(users.getEmail())){
            throw new InvalidUserException("Email Already Exists");
        }
        if(phoneExist(users.getPhone())){
            throw new InvalidUserException("Phone Already Exists");
        }
        if (dataValidator.validateName(users.getFirstName()) || dataValidator.validateName(users.getLastName())) {
            throw new InvalidUserException("Invalid Name Format.");
        }
        if (!dataValidator.validateEmail(users.getEmail())) {
            throw new InvalidUserException("Invalid Email Format.");
        }
        if (!dataValidator.validatePhone(users.getPhone())) {
            throw new InvalidUserException("Invalid Phone Number Format.");
        }
        if (!dataValidator.validatePassword(users.getPassword())) {
            throw new InvalidUserException("Invalid Password Format.");
        }
        users.setPassword(encryptionService.encryptPassword(users.getPassword()));
        return userRepository.save(users);
    }

    @Override
    public String login(LoginDto loginDto){
        Optional<Users> users = userRepository.findByEmail(loginDto.getEmail());
        if(users.isPresent()){
            Users user = users.get();
            if(!user.isVerified()) {
                return "User Not Verified";
            }
            if(encryptionService.verifyPassword(loginDto.getPassword(), user.getPassword())){
                return jwtService.generateJWT(user);
            }
        }
        return "Not Authenticated";
    }

    @Override
    public String delete(){
        userRepository.deleteAll();
        return "Deleted";
    }

    @Override
    public List<?> getUsers(Users users) {
        return userRepository.findAll();
    }


    @Override
    public Users updateUsers(Users users) {
        return null;
    }

    @Override
    public boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean phoneExist(String phone) {
        return userRepository.findByPhone(phone).isPresent();
    }
}