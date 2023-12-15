package com.sahyunjin.smutify.service.logic;

import com.sahyunjin.smutify.domain.song.Song;
import com.sahyunjin.smutify.domain.song.SongJapRepository;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import com.sahyunjin.smutify.dto.song.SongGenreRequestDto;
import com.sahyunjin.smutify.dto.song.SongResponseDto;
import com.sahyunjin.smutify.dto.song.SongSmallResponseDto;
import com.sahyunjin.smutify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public SongSmallResponseDto findById(Long songId) {

        Song entity = songJapRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("ERROR - 해당 id의 노래는 존재하지 않습니다."));

        return new SongSmallResponseDto(entity);
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

    @Transactional
    @Override
    public List<SongResponseDto> sortAndsearch(List<SongResponseDto> songResponseDtos, String order, String search) {

        List<SongResponseDto> resultSongResponseDtos = new ArrayList<>();

        if (order == null && search == null) {  // 전체 메모들 리스트
            resultSongResponseDtos = songResponseDtos;
        }
        else if (order != null && search == null) {  // 정렬
            if (order.equals("all-song")) {
                resultSongResponseDtos = songResponseDtos;
            }
            else if (order.equals("singer-song")) {
                Comparator<SongResponseDto> comparator = Comparator.comparing(SongResponseDto::getSinger);
                Collections.sort(songResponseDtos, comparator);
                resultSongResponseDtos.addAll(songResponseDtos);
            }
            else if (order.equals("title-song")) {
                Comparator<SongResponseDto> comparator = Comparator.comparing(SongResponseDto::getTitle);
                Collections.sort(songResponseDtos, comparator);
                resultSongResponseDtos.addAll(songResponseDtos);
            }
            else if (order.equals("genre-song")) {
                Comparator<SongResponseDto> comparator = Comparator.comparing(SongResponseDto::getGenre, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
                resultSongResponseDtos.addAll(songResponseDtos.stream().sorted(comparator).collect(Collectors.toList()));
                // null값의 데이터는 뒤로 보내고, 나머지는 오름차순 정렬함.
            }
            else {  // 잘못된 정렬 기준을 입력받았을 경우라면
                throw new RuntimeException("ERROR - 잘못된 정렬기준 입력받음.");  // 잘못된 메모정렬기준 입력 예외처리.
            }
        }
        else if (order == null && search != null) {  // 검색
            resultSongResponseDtos = songResponseDtos.stream()
                    .filter(songResponseDto ->
                            (songResponseDto.getSinger() != null && songResponseDto.getSinger().contains(search)) ||
                                    (songResponseDto.getTitle() != null && songResponseDto.getTitle().contains(search)) ||
                                    (songResponseDto.getGenre() != null && songResponseDto.getGenre().contains(search))
                    )
                    .collect(Collectors.toList());
        }

        return resultSongResponseDtos;
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
