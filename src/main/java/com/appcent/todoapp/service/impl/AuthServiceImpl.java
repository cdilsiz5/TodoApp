package com.appcent.todoapp.service.impl;

import com.appcent.todoapp.exception.TokenRefreshException;
import com.appcent.todoapp.exception.UserNotFoundException;
import com.appcent.todoapp.model.RefreshToken;
import com.appcent.todoapp.model.User;
import com.appcent.todoapp.repository.UserRepository;
import com.appcent.todoapp.request.LoginRequest;
import com.appcent.todoapp.request.TokenRefreshRequest;
import com.appcent.todoapp.response.JwtResponse;
import com.appcent.todoapp.response.TokenRefreshResponse;
import com.appcent.todoapp.security.UserDetailsImpl;
import com.appcent.todoapp.security.service.JwtService;
import com.appcent.todoapp.security.service.RefreshTokenService;
import com.appcent.todoapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.appcent.todoapp.constants.Constant.USER_NOT_FOUND;
import static com.appcent.todoapp.mapper.UserMapper.USER_MAPPER;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public ResponseEntity<?> Login(LoginRequest request){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getUserPassword()));
            log.info("done authenticate user :{}", request.getUserEmail());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String jwt = jwtService.generateJwtToken(userDetails);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

            User user = userRepository.findByEmail(request.getUserEmail()).orElseThrow(()->new UserNotFoundException(USER_NOT_FOUND));
            return ResponseEntity.ok(new JwtResponse(jwt,refreshToken.getToken(), USER_MAPPER.toUserDto(user)));

        } catch (DisabledException e) {
            log.error("done authentication failed user :{}", request.getUserEmail());
            throw new DisabledException("USER_ACCOUNT_DISABLED");
        } catch (BadCredentialsException e) {
            log.error("done authentication failed user :{}", request.getUserEmail());
            throw new BadCredentialsException("WRONG_USERNAME_OR_PASSWORD");
        } catch (UsernameNotFoundException e) {
            log.error("done authentication failed user :{}", request.getUserEmail());
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }

    }
    public ResponseEntity<?> logoutUser(Long userId) {
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok("Log out successful!");
    }
    public ResponseEntity<?> refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateTokenFromUsername(user.getEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}

