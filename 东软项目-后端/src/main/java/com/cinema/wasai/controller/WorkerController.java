package com.cinema.wasai.controller;

import com.cinema.wasai.model.entiy.Film;
import com.cinema.wasai.model.entiy.Worker;
import com.cinema.wasai.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/worker")
@CrossOrigin
@Slf4j
public class WorkerController {
    @Autowired
    WorkerService workerService;

    @GetMapping("")
    public List<Worker> getAllAvaliableWorkers(){
        log.info("FilmController->getAllFilm()");
        return workerService.selectAllAvalibaleWorkers();
    }
}
