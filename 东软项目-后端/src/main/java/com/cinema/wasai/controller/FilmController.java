package com.cinema.wasai.controller;

import com.cinema.wasai.model.entiy.Film;
import com.cinema.wasai.model.entiy.FilmEvaluate;
import com.cinema.wasai.model.entiy.Poster;
import com.cinema.wasai.service.FilmEvaluateService;
import com.cinema.wasai.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/film")
@CrossOrigin
public class FilmController {

    @Autowired
    FilmService filmService;
    @Autowired
    FilmEvaluateService filmEvaluateService;
    //查询所有电影
    @GetMapping("")
    public List<Film> getAllFilm(){
        log.info("FilmController->getAllFilm()");
        return filmService.selectAllFilms();

    }
    //查询热门榜单
    @GetMapping("/hot/limit")
    public List<Film> getHotRank() {
        log.info("FilmController->getHotRank()");
        return filmService.selectAllHotRanks(10);
    }

    //头部模糊查询
    @GetMapping("/name")
    public List<Film> HeaderSearch(){
        log.info("FilmController->HeaderSearch()");
        return filmService.selectAllFilms();
    }
}
