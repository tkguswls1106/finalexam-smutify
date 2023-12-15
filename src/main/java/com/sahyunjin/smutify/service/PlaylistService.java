package com.sahyunjin.smutify.service;

import com.sahyunjin.smutify.dto.playlist.PlaylistAddSongRequestDto;
import com.sahyunjin.smutify.dto.playlist.PlaylistNSongRequestDto;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;

public interface PlaylistService {

    PlaylistResponseDto findById(Long playlistId);
    void addSongForPlaylist(Long userId, Long playlistId, PlaylistAddSongRequestDto playlistAddSongRequestDto);
    void removeSongForPlaylist(Long playlistId, Long songId);
    void createPlaylistWithSong(Long userId, Long songId, PlaylistNSongRequestDto playlistNSongRequestDto);
}
