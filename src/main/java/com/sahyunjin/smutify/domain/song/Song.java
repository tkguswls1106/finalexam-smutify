package com.sahyunjin.smutify.domain.song;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "song_table")
@Entity
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "singer")
    private String singer;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "playlistIds", columnDefinition = "TEXT default null")
    private String playlistIds;
    // 파싱법: 1p2p...


    // 수정(업데이트) 기능
    public void updatePlaylistIds(String playlistIdStr) {  // 패스워드 변경 기능
        this.playlistIds = playlistIdStr;
    }
    public void updateGenre(String genre) {  // 패스워드 변경 기능
        this.genre = genre;
    }
}
