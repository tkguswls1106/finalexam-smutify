package com.sahyunjin.smutify.controller;

import com.sahyunjin.smutify.domain.user.User;
import com.sahyunjin.smutify.dto.playlist.PlaylistNSongRequestDto;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import com.sahyunjin.smutify.dto.song.SongGenreRequestDto;
import com.sahyunjin.smutify.dto.song.SongResponseDto;
import com.sahyunjin.smutify.dto.user.UserResponseDto;
import com.sahyunjin.smutify.service.PlaylistService;
import com.sahyunjin.smutify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SongController {

    private final PlaylistService playlistService;
    private final SongService songService;


    @GetMapping("/users/{userId}/search")  // song 목록들 모두 조회
    public String getAllSongs(@PathVariable Long userId, Model model, HttpSession session) {

        UserResponseDto loginUser;
        try {  // 로그인 체크
            loginUser = loginCheckSession(session);
        }
        catch (RuntimeException e) {  // 로그인이 안되어있을시, 로그인창으로 강제 리다이렉트
            return "redirect:/login";
        }

        List<SongResponseDto> songResponseDtos = songService.getAllSongs();
        model.addAttribute("songResponseDtos", songResponseDtos);
        model.addAttribute("userId", userId);

        List<Long> playlistIds = loginUser.getPlaylistIds();
        List<PlaylistResponseDto> playlistResponseDtos = new ArrayList<PlaylistResponseDto>();
        for (Long playlistId : playlistIds) {
            PlaylistResponseDto playlistResponseDto = playlistService.findById(playlistId);
            playlistResponseDtos.add(playlistResponseDto);
        }
        playlistResponseDtos.sort(Comparator.comparing(PlaylistResponseDto::getTitle));
        model.addAttribute("playlists", playlistResponseDtos);

        return "search";
    }

    @PutMapping("/songs/{songId}")
    public String updateGenre(@PathVariable Long songId, @ModelAttribute SongGenreRequestDto songGenreRequestDto, HttpSession session) {

        UserResponseDto loginUser;
        try {  // 로그인 체크
            loginUser = loginCheckSession(session);
        }
        catch (RuntimeException e) {  // 로그인이 안되어있을시, 로그인창으로 강제 리다이렉트
            return "redirect:/login";
        }

        try {
            songService.updateGenre(songId, songGenreRequestDto);
        } catch (RuntimeException e) {
            System.out.println("ERROR - 중복! 이미 존재하는 장르입니다.");
            return "redirect:/users/" + loginUser.getId() + "/search";  // search 페이지로 리다이렉트
        }
        return "redirect:/users/" + loginUser.getId() + "/search";  // search 페이지로 리다이렉트
    }


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
