package com.sahyunjin.smutify.controller;

import com.sahyunjin.smutify.domain.user.User;
import com.sahyunjin.smutify.dto.user.UserResponseDto;
import com.sahyunjin.smutify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;




    // ----- 기타 사용 메소드들 ----- //

    public UserResponseDto loginCheckSession(HttpSession session) {  // 로그인 체크용 메소드
        UserResponseDto userResponseDto = (UserResponseDto) session.getAttribute("user");
        if (userResponseDto != null) {  // 로그인되어있는 경우
            return userResponseDto;
        } else {
            throw new RuntimeException("ERROR - 로그인이 되어있지않습니다.");
        }
    }
}
