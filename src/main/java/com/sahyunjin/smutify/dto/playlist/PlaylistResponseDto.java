package com.sahyunjin.smutify.dto.playlist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sahyunjin.smutify.domain.playlist.Playlist;
import com.sahyunjin.smutify.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistResponseDto {

    private Long id;
    private String title;
    private String content;
    private List<Long> songIds;


    @JsonIgnore
    public List<Long> getSongIdList(String songIdStr) {
        if (songIdStr == null) {
            return new ArrayList<Long>();
        }
        String beforeParsing = songIdStr;
        String[] afterParsing = beforeParsing.split("p");

        List<Long> songIdList = new ArrayList<>();
        for (String songId : afterParsing) {
            songIdList.add(Long.parseLong(songId));
        }

        return songIdList;
    }


    public PlaylistResponseDto(Playlist entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.songIds = getSongIdList(entity.getSongIds());
    }
}
