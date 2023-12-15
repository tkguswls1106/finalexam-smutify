package com.sahyunjin.smutify.service;

import com.sahyunjin.smutify.domain.song.Song;
import com.sahyunjin.smutify.dto.song.SongResponseDto;

import java.util.List;

public interface SongService {

    List<SongResponseDto> getAllSongs();
}
