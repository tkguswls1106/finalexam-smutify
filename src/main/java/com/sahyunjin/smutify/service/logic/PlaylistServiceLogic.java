package com.sahyunjin.smutify.service.logic;

import com.sahyunjin.smutify.domain.playlist.Playlist;
import com.sahyunjin.smutify.domain.playlist.PlaylistJpaRepository;
import com.sahyunjin.smutify.domain.song.Song;
import com.sahyunjin.smutify.domain.song.SongJapRepository;
import com.sahyunjin.smutify.domain.user.User;
import com.sahyunjin.smutify.domain.user.UserJpaRepository;
import com.sahyunjin.smutify.dto.playlist.PlaylistNSongRequestDto;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import com.sahyunjin.smutify.dto.user.UserLoginRequestDto;
import com.sahyunjin.smutify.dto.user.UserResponseDto;
import com.sahyunjin.smutify.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistServiceLogic implements PlaylistService {

    private final UserJpaRepository userJpaRepository;
    private final PlaylistJpaRepository playlistJpaRepository;
    private final SongJapRepository songJapRepository;


    @Transactional
    @Override
    public PlaylistResponseDto findById(Long playlistId) {

        Playlist entity = playlistJpaRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("ERROR - 해당 id의 플레이리스트는 존재하지 않습니다."));

        return new PlaylistResponseDto(entity);
    }

    @Transactional
    @Override
    public void createPlaylistWithSong(Long userId, Long songId, PlaylistNSongRequestDto playlistNSongRequestDto) {

        Playlist entity = playlistNSongRequestDto.toEntity(userId, songId);
        Playlist entityP = playlistJpaRepository.save(entity);

        User entityU = userJpaRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("ERROR - 해당 id의 사용자는 존재하지 않습니다."));
        Song entityS = songJapRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("ERROR - 해당 id의 노래는 존재하지 않습니다."));

        String str1;
        if(entityU.getPlaylistIds() != null) {
            str1 = (entityU.getPlaylistIds() + String.valueOf(entityP.getId()) + "p");
        }
        else {
            str1 = String.valueOf(entityP.getId()) + "p";
        }
        entityU.updatePlaylistIds(str1);

        String str2;
        if(entityS.getPlaylistIds() != null) {
            str2 = (entityS.getPlaylistIds() + String.valueOf(entityP.getId()) + "p");
        }
        else {
            str2 = String.valueOf(entityP.getId()) + "p";
        }
        entityS.updatePlaylistIds(str2);
    }
}
