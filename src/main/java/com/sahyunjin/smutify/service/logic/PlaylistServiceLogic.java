package com.sahyunjin.smutify.service.logic;

import com.sahyunjin.smutify.domain.playlist.Playlist;
import com.sahyunjin.smutify.domain.playlist.PlaylistJpaRepository;
import com.sahyunjin.smutify.domain.user.User;
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

    private final PlaylistJpaRepository playlistJpaRepository;


    @Transactional
    @Override
    public PlaylistResponseDto findById(Long playlistId) {

        Playlist entity = playlistJpaRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("ERROR - 해당 id의 플레이리스트는 존재하지 않습니다."));

        return new PlaylistResponseDto(entity);
    }
}
