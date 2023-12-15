package com.sahyunjin.smutify.dto.song;

import com.sahyunjin.smutify.domain.song.Song;
import com.sahyunjin.smutify.dto.playlist.PlaylistResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
@NoArgsConstructor
public class SongSmallResponseDto {

    private Long id;
    private String singer;
    private String title;
    private String genre;

    public SongSmallResponseDto(Song entity) {
        this.id = entity.getId();
        this.singer = entity.getSinger();
        this.title = entity.getTitle();
        this.genre = entity.getGenre();
    }
}
