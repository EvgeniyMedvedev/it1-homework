package it1.aop.homework.controllers;

import it1.aop.homework.dto.MethodStatistic;
import it1.aop.homework.entity.MethodMetric;
import it1.aop.homework.services.AggregatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/time")
@AllArgsConstructor
public class ApiController {

    private final AggregatorService aggregatorService;

    @GetMapping("/avg")
    public MethodStatistic averageTime() {
        return aggregatorService.avgExecMethod();
    }

    @GetMapping("/all")
    public Iterable<MethodMetric> getAll() {
        return aggregatorService.getAll();
    }

    @GetMapping("/min")
    public MethodStatistic minTime() {
        return aggregatorService.minExecMethod();
    }

    @GetMapping("/max")
    public MethodStatistic maxTime() {
        return aggregatorService.maxExecMethod();
    }

    @PostMapping("/newMethod")
    public ResponseEntity<Void> newMethod() {
        aggregatorService.addRandomMethod();
        return ResponseEntity.ok().build();
    }
}
