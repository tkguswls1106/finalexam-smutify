package com.sahyunjin.smutify.service;

import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;

public interface PlaylistService {

    PlaylistResponseDto findById(Long playlistId);
}
