package com.sahyunjin.smutify.controller;

import com.sahyunjin.smutify.domain.playlist.Playlist;
import com.sahyunjin.smutify.domain.user.User;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import com.sahyunjin.smutify.dto.user.UserLoginRequestDto;
import com.sahyunjin.smutify.dto.user.UserResponseDto;
import com.sahyunjin.smutify.dto.user.UserSignupRequestDto;
import com.sahyunjin.smutify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/")  // 기본페이지 접속시 로그인 페이지로 리이렉트시킴
    public String homeRedirect(Model model) {
        return "redirect:/login";  // login 페이지로 리다이렉트
    }

    @GetMapping("/signup")  // 회원가입 페이지로 이동
    public String signUpForm(Model model) {
        return "signup";
    }
    @GetMapping("/signup_error")  // 회원가입에러 페이지로 이동
    public String signupError() {
        return "signup_error";
    }
    @PostMapping("/signup")  // 회원가입
    public String signUp(@ModelAttribute UserSignupRequestDto userSignupRequestDto) {
        try {
            userService.signUp(userSignupRequestDto);
            return "redirect:/login";  // login 페이지로 리다이렉트
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return "redirect:/signup_error";  // signup_error 페이지로 리다이렉트
        }

    }

    @GetMapping("/login")  // 로그인 페이지로 이동
    public String loginForm() {
        return "login";
    }
    @GetMapping("/login_error")  // 로그인에러 페이지로 이동
    public String loginError() {
        return "login_error";
    }
    @PostMapping("/login")  // 로그인 처리
    public String login(@ModelAttribute UserLoginRequestDto userLoginRequestDto, HttpSession session) {
        try {
            UserResponseDto loginUser = userService.login(userLoginRequestDto);

            session.setAttribute("user", loginUser);

            return "redirect:/users/" + loginUser.getId() + "/main";  // main 페이지로 리다이렉트
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return "redirect:/login_error";  // login_error 페이지로 리다이렉트
        }
    }


    // ----- 기타 사용 메소드들 ----- //

    public User loginCheckSession(HttpSession session) {  // 로그인 체크용 메소드
        User user = (User) session.getAttribute("user");
        if (user != null) {  // 로그인되어있는 경우
            return user;
        } else {
            throw new RuntimeException("ERROR - 로그인이 되어있지않습니다.");
        }
    }

    public static <T> List<Boolean> containsInA(List<T> A, List<T> B) {
        List<Boolean> boolResults = new ArrayList<>();

        for (T itemB : B) {
            boolean contains = A.contains(itemB);
            boolResults.add(contains);
        }

        return boolResults;
    }
}
