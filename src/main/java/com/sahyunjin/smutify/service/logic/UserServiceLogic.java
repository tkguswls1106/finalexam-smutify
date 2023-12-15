package com.sahyunjin.smutify.service.logic;

import com.sahyunjin.smutify.domain.playlist.Playlist;
import com.sahyunjin.smutify.domain.user.User;
import com.sahyunjin.smutify.domain.user.UserJpaRepository;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import com.sahyunjin.smutify.dto.user.UserLoginRequestDto;
import com.sahyunjin.smutify.dto.user.UserResponseDto;
import com.sahyunjin.smutify.dto.user.UserSignupRequestDto;
import com.sahyunjin.smutify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceLogic implements UserService {

    private final UserJpaRepository userJpaRepository;


    @Transactional
    @Override
    public UserResponseDto signUp(UserSignupRequestDto userSignupRequestDto) {

        String newUsername = userSignupRequestDto.getUsername();
        userJpaRepository.findByUsername(newUsername)
                .ifPresent(user -> {  // 해당 로그인이름의 사용자가 이미 존재한다면,
                    throw new RuntimeException("ERROR - 중복! 이미 존재하는 계정 입니다.");
                });

        User entity = userJpaRepository.save(userSignupRequestDto.toEntity());
        return new UserResponseDto(entity);
    }

    @Transactional
    @Override
    public UserResponseDto login(UserLoginRequestDto userLoginRequestDto) {

        String loginUsername = userLoginRequestDto.getUsername();

        User entity = userJpaRepository.findByUsername(loginUsername)
                .orElseThrow(() -> new RuntimeException("ERROR - 해당 이름의 사용자는 존재하지 않습니다."));

        return new UserResponseDto(entity);
    }
}
