package com.sahyunjin.smutify.service.logic;

import com.sahyunjin.smutify.domain.user.User;
import com.sahyunjin.smutify.domain.user.UserJpaRepository;
import com.sahyunjin.smutify.dto.user.UserLoginRequestDto;
import com.sahyunjin.smutify.dto.user.UserResponseDto;
import com.sahyunjin.smutify.dto.user.UserSignupRequestDto;
import com.sahyunjin.smutify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceLogic implements UserService {

    private final UserJpaRepository userJpaRepository;


    @Override
    public User signUp(UserSignupRequestDto userSignupRequestDto) {

        String newUsername = userSignupRequestDto.getUsername();
        userJpaRepository.findByUsername(newUsername)
                .ifPresent(user -> {  // 해당 로그인이름의 사용자가 이미 존재한다면,
                    throw new RuntimeException("ERROR - 이미 존재하는 계정 입니다.");
                });

        User entity = userJpaRepository.save(userSignupRequestDto.toEntity());
        return entity;
    }

    @Override
    public User login(UserLoginRequestDto userLoginRequestDto) {

        String loginUsername = userLoginRequestDto.getUsername();
        boolean isExistsUser = userJpaRepository.existsByUsername(loginUsername);
        if(isExistsUser) {
            User entity = userLoginRequestDto.toEntity();
            return entity;
        }
        else {
            throw new RuntimeException("ERROR - 존재하지않는 계정 입니다.");
        }
    }
}
