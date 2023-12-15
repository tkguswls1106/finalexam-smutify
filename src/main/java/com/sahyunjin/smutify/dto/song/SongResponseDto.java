package com.sahyunjin.smutify.dto.song;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sahyunjin.smutify.domain.song.Song;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SongResponseDto {

    private Long id;
    private String singer;
    private String title;
    private String genre;
    private List<Long> playlistIds;
    private List<PlaylistResponseDto> playlists;


    @JsonIgnore
    public List<Long> getPlaylistIdList(String playlistIdStr) {
        if (playlistIdStr == null) {
            return new ArrayList<Long>();
        }
        String beforeParsing = playlistIdStr;
        String[] afterParsing = beforeParsing.split("p");

        List<Long> playlistIdList = new ArrayList<>();
        for (String playlistId : afterParsing) {
            playlistIdList.add(Long.parseLong(playlistId));
        }

        return playlistIdList;
    }


    public SongResponseDto(Song entity) {
        this.id = entity.getId();
        this.singer = entity.getSinger();
        this.title = entity.getTitle();
        this.genre = entity.getGenre();
        this.playlistIds = getPlaylistIdList(entity.getPlaylistIds());
        this.playlists = new ArrayList<PlaylistResponseDto>();
    }
}
