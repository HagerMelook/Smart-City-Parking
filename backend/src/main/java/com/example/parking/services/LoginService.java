package com.example.parking.services;

import com.example.parking.dao.UserDAO;
import com.example.parking.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    UserDAO userDAO;

    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDTO checkAccount(UserDTO account){
        int userId = userDAO.getUserIdByEmail(account.getEmail());
        String userType = userDAO.getUserTypeById(userId);

        UserDTO user = UserDTO.builder()
                .userId(userId)
                .userType(userType)
                .build();

        return user;
    }
}
