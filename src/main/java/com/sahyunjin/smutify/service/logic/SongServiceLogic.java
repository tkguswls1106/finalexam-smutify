package com.sahyunjin.smutify.service.logic;

import com.sahyunjin.smutify.domain.playlist.PlaylistJpaRepository;
import com.sahyunjin.smutify.domain.song.Song;
import com.sahyunjin.smutify.domain.song.SongJapRepository;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import com.sahyunjin.smutify.dto.song.SongResponseDto;
import com.sahyunjin.smutify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceLogic implements SongService {

    private final PlaylistServiceLogic playlistServiceLogic;
    private final SongJapRepository songJapRepository;


    @Transactional
    @Override
    public List<SongResponseDto> getAllSongs() {

        List<Song> songs = songJapRepository.findAll();
        List<SongResponseDto> songResponseDtos = new ArrayList<SongResponseDto>();
        for(Song song : songs) {
            SongResponseDto songResponseDto = new SongResponseDto(song);
            songResponseDtos.add(songResponseDto);
        }

        for(SongResponseDto songResponseDto : songResponseDtos) {
            List<PlaylistResponseDto> playlistResponseDtos = new ArrayList<PlaylistResponseDto>();
            for(Long playlistId : songResponseDto.getPlaylistIds()) {
                PlaylistResponseDto playlistResponseDto = playlistServiceLogic.findById(playlistId);
                playlistResponseDtos.add(playlistResponseDto);
            }

            songResponseDto.setPlaylists(playlistResponseDtos);
        }

        return songResponseDtos;
    }
}
