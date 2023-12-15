package com.sahyunjin.smutify.service;

import com.sahyunjin.smutify.dto.user.UserLoginRequestDto;
import com.sahyunjin.smutify.dto.user.UserResponseDto;
import com.sahyunjin.smutify.dto.user.UserSignupRequestDto;

public interface UserService {

    UserResponseDto signUp(UserSignupRequestDto userSignupRequestDto);
    UserResponseDto login(UserLoginRequestDto userLoginRequestDto);
}
