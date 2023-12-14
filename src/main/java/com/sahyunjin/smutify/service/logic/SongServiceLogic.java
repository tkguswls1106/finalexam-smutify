package com.sahyunjin.smutify.service.logic;

import com.sahyunjin.smutify.domain.song.SongJapRepository;
import com.sahyunjin.smutify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongServiceLogic implements SongService {

    private final SongJapRepository songJapRepository;
}
