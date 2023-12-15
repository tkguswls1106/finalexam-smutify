package com.sahyunjin.smutify.service.logic;

import com.sahyunjin.smutify.domain.playlist.PlaylistJpaRepository;
import com.sahyunjin.smutify.domain.song.Song;
import com.sahyunjin.smutify.domain.song.SongJapRepository;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import com.sahyunjin.smutify.dto.song.SongGenreRequestDto;
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

    @Transactional
    @Override
    public void updateGenre(Long songId, SongGenreRequestDto songGenreRequestDto) {

        Song entity = songJapRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("ERROR - 해당 id의 노래는 존재하지 않습니다."));

        // 중복추가 막기
        List<String> getGenres = getGenreList(entity.getGenre());
        if (getGenres.contains(songGenreRequestDto.getGenre())) {
            throw new RuntimeException("ERROR - 중복! 이미 존재하는 장르입니다.");
        }

        String str;
        if(entity.getGenre() != null) {
            str = (entity.getGenre() + String.valueOf(songGenreRequestDto.getGenre()) + ",");
        }
        else {
            str = String.valueOf(songGenreRequestDto.getGenre()) + ",";
        }
        entity.updateGenre(str);
    }


    // ----- 기타 사용 메소드들 ----- //

    public List<String> getGenreList(String genreStr) {
        if (genreStr == null) {
            return new ArrayList<String>();
        }
        String beforeParsing = genreStr;
        String[] afterParsing = beforeParsing.split(",");

        List<String> genreList = new ArrayList<>();
        for (String genre : afterParsing) {
            genreList.add(genre);
        }

        return genreList;
    }
}
