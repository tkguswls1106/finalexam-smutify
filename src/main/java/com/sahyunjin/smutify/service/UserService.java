package com.sahyunjin.smutify.service;

import com.sahyunjin.smutify.domain.user.User;
import com.sahyunjin.smutify.dto.user.UserLoginRequestDto;
import com.sahyunjin.smutify.dto.user.UserSignupRequestDto;

public interface UserService {

    User signUp(UserSignupRequestDto userSignupRequestDto);
    User login(UserLoginRequestDto userLoginRequestDto);
}
