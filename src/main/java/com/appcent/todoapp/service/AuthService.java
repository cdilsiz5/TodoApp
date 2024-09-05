package com.appcent.todoapp.service;

import com.appcent.todoapp.request.LoginRequest;
import com.appcent.todoapp.request.TokenRefreshRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> Login(LoginRequest request);
    ResponseEntity<?> logoutUser(Long userID) ;
    ResponseEntity<?> refreshToken(TokenRefreshRequest request) ;


    }
