package com.sahyunjin.smutify.controller;

import com.sahyunjin.smutify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
}
