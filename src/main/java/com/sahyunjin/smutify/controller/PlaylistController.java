package com.sahyunjin.smutify.controller;

import com.sahyunjin.smutify.domain.playlist.Playlist;
import com.sahyunjin.smutify.domain.user.User;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import com.sahyunjin.smutify.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;


    @GetMapping("/users/{userId}/main")  // playlist 목록들 모두 조회 (제목 오름차순 정렬)
    public String getAllPlaylists(@PathVariable Long userId, Model model, HttpSession session) {

        User loginUser;
        try {  // 로그인 체크
            loginUser = loginCheckSession(session);
        }
        catch (RuntimeException e) {  // 로그인이 안되어있을시, 로그인창으로 강제 리다이렉트
            return "redirect:/login";
        }

        String playlistIdsStr = loginUser.getPlaylistIds();
        List<Long> playlistIds = getPlaylistIdList(playlistIdsStr);

        List<PlaylistResponseDto> playlistResponseDtos = new ArrayList<PlaylistResponseDto>();
        for (Long playlistId : playlistIds) {
            PlaylistResponseDto playlistResponseDto = playlistService.findById(playlistId);
            playlistResponseDtos.add(playlistResponseDto);
        }
        playlistResponseDtos.sort(Comparator.comparing(PlaylistResponseDto::getTitle));

        model.addAttribute("playlistResponseDtos", playlistResponseDtos);
        model.addAttribute("userId", userId);

        return "main";
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

    public List<Long> getPlaylistIdList(String playlistIdStr) {
        if (playlistIdStr == null) {
            return new ArrayList<Long>();
        }
        String beforeParsing = playlistIdStr;
        String[] afterParsing = beforeParsing.split("p");

        List<Long> playlistIdList = new ArrayList<>();
        for (String playlistId : afterParsing) {
            playlistIdList.add(Long.parseLong(playlistId));
        }

        return playlistIdList;
    }
}