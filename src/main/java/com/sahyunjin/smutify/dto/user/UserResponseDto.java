package com.sahyunjin.smutify.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sahyunjin.smutify.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String username;
    private List<Long> playlistIds;


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


    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.playlistIds = getPlaylistIdList(entity.getPlaylistIds());
    }
}
