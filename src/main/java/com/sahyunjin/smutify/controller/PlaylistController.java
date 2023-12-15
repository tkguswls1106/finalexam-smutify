package com.sahyunjin.smutify.controller;

import com.sahyunjin.smutify.domain.playlist.Playlist;
import com.sahyunjin.smutify.domain.user.User;
import com.sahyunjin.smutify.dto.playlist.PlaylistAddSongRequestDto;
import com.sahyunjin.smutify.dto.playlist.PlaylistNSongRequestDto;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import com.sahyunjin.smutify.dto.song.SongSmallResponseDto;
import com.sahyunjin.smutify.dto.user.UserResponseDto;
import com.sahyunjin.smutify.service.PlaylistService;
import com.sahyunjin.smutify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SongService songService;


    @GetMapping("/users/{userId}/main")  // playlist 목록들 모두 조회 (제목 오름차순 정렬)
    public String getAllPlaylists(@PathVariable Long userId, Model model, HttpSession session) {

        UserResponseDto loginUser;
        try {  // 로그인 체크
            loginUser = loginCheckSession(session);
        }
        catch (RuntimeException e) {  // 로그인이 안되어있을시, 로그인창으로 강제 리다이렉트
            return "redirect:/login";
        }

        List<Long> playlistIds = loginUser.getPlaylistIds();

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

    @GetMapping("/playlists/{playlistId}")  // playlist 1개 조회 (제목 오름차순 정렬)
    public String getOnePlaylist(@PathVariable Long playlistId, Model model, HttpSession session) {

        UserResponseDto loginUser;
        try {  // 로그인 체크
            loginUser = loginCheckSession(session);
        }
        catch (RuntimeException e) {  // 로그인이 안되어있을시, 로그인창으로 강제 리다이렉트
            return "redirect:/login";
        }

        PlaylistResponseDto playlistResponseDto = playlistService.findById(playlistId);
        List<Long> songIdList = playlistResponseDto.getSongIds();

        List<SongSmallResponseDto> songSmallResponseDtos = new ArrayList<SongSmallResponseDto>();
        for(Long songId : songIdList) {
            SongSmallResponseDto songSmallResponseDto = songService.findById(songId);
            songSmallResponseDtos.add(songSmallResponseDto);
        }

        model.addAttribute("playlistResponseDto", playlistResponseDto);
        model.addAttribute("songSmallResponseDtos", songSmallResponseDtos);
        model.addAttribute("userId", loginUser.getId());

        return "playlist";
    }

    @PostMapping("/users/{userId}/playlists/{playlistId}")
    public String addSongForPlaylist(@PathVariable Long userId, @PathVariable Long playlistId, @ModelAttribute PlaylistAddSongRequestDto playlistAddSongRequestDto, HttpSession session) {

        UserResponseDto loginUser;
        try {  // 로그인 체크
            loginUser = loginCheckSession(session);
        }
        catch (RuntimeException e) {  // 로그인이 안되어있을시, 로그인창으로 강제 리다이렉트
            return "redirect:/login";
        }

        try {
            playlistService.addSongForPlaylist(userId, playlistId, playlistAddSongRequestDto);
        } catch (RuntimeException e) {
            System.out.println("ERROR - 중복! 이미 플레이리스트에 존재하는 노래입니다.");
            return "redirect:/users/" + loginUser.getId() + "/search";  // search 페이지로 리다이렉트
        }
        return "redirect:/users/" + loginUser.getId() + "/search";  // search 페이지로 리다이렉트
    }

    @PostMapping("/users/{userId}/playlists/songs/{songId}")
    public String createPlayListWithSong(@PathVariable Long userId, @PathVariable Long songId, @ModelAttribute PlaylistNSongRequestDto playlistNSongRequestDto, HttpSession session) {

        UserResponseDto loginUser;
        try {  // 로그인 체크
            loginUser = loginCheckSession(session);
        }
        catch (RuntimeException e) {  // 로그인이 안되어있을시, 로그인창으로 강제 리다이렉트
            return "redirect:/login";
        }

        playlistService.createPlaylistWithSong(userId, songId, playlistNSongRequestDto);
        return "redirect:/users/" + loginUser.getId() + "/search";  // search 페이지로 리다이렉트
    }

    @PutMapping("/playlists/{playlistId}/songs/{songId}")
    public String removeSongForPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {

        PlaylistResponseDto playlistResponseDto = playlistService.findById(playlistId);
        List<Long> songIds = playlistResponseDto.getSongIds();
        songIds.removeIf(id -> id.equals(songId));

        String str = null;
        for(Long songId2 : songIds) {
            if(str == null) str = (String.valueOf(songId2) + ",");
            else str += (String.valueOf(songId2) + ",");
        }


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
