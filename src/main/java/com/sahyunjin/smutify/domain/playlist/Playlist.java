package com.sahyunjin.smutify.domain.playlist;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "playlist")
@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;


    @Column(name = "makerUserId")
    private Long makerUserId;

    @Column(name = "songIds", columnDefinition = "TEXT default null")
    private String songIds;
    // 파싱법: 1p2p...


    @Builder(builderClassName = "PlaylistNSongBuilder", builderMethodName = "PlaylistNSongBuilder")
    public Playlist(String title, String content, Long userId, Long songId) {
        this.title = title;
        this.content = content;
        this.makerUserId = userId;
        this.songIds = (String.valueOf(songId) + "p");
    }
}
