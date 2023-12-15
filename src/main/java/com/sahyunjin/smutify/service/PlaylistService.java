package com.sahyunjin.smutify.service;

import com.sahyunjin.smutify.dto.playlist.PlaylistNSongRequestDto;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;

public interface PlaylistService {

    PlaylistResponseDto findById(Long playlistId);
    void createPlaylistWithSong(Long userId, Long songId, PlaylistNSongRequestDto playlistNSongRequestDto);
}
