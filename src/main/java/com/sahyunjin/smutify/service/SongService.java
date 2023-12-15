package com.sahyunjin.smutify.service;

import com.sahyunjin.smutify.dto.song.SongGenreRequestDto;
import com.sahyunjin.smutify.dto.song.SongResponseDto;
import com.sahyunjin.smutify.dto.song.SongSmallResponseDto;

import java.util.List;

public interface SongService {

    List<SongResponseDto> getAllSongs();
    SongSmallResponseDto findById(Long songId);
    void updateGenre(Long songId, SongGenreRequestDto songGenreRequestDto);
    List<SongResponseDto> sortAndsearch(List<SongResponseDto> songResponseDtos, String order, String search);
}
