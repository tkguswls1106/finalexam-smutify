package com.sahyunjin.smutify.dto.playlist;

import com.sahyunjin.smutify.domain.playlist.Playlist;
import com.sahyunjin.smutify.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistNSongRequestDto {

    private String title;
    private String content;

    public Playlist toEntity(Long userId, Long songId) {
        return Playlist.PlaylistNSongBuilder()
                .title(title)
                .content(content)
                .userId(userId)
                .songId(songId)
                .build();
    }
}
