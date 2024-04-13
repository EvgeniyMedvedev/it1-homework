package it1.aop.homework.services;

import it1.aop.homework.dto.MethodStatistic;
import it1.aop.homework.entity.MethodMetric;
import it1.aop.homework.repo.MethodMetricRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AggregatorServiceImpl implements AggregatorService{

    private final MethodMetricRepo methodMetricRepo;
    private final ActionSimulator actionSimulator;

    @Override
    public MethodStatistic maxExecMethod() {
        return methodMetricRepo.findTopByOrderByMethodNameAsc();
    }

    @Override
    public MethodStatistic minExecMethod() {
        return methodMetricRepo.findTopByOrderByMethodNameDesc();
    }

    @Override
    public MethodStatistic avgExecMethod() {
        return methodMetricRepo.getAvgExecutionTimeMethod();
    }

    @Override
    public Iterable<MethodMetric> getAll() {
        return methodMetricRepo.findAll();
    }

    @Override
    public void add(MethodMetric methodMetric) {
        log.info("Adding method: {}", methodMetric);
        var save = methodMetricRepo.save(methodMetric);
        log.info("Added method: {}", save);
    }

    @Override
    public void addRandomMethod() {
        actionSimulator.doRandom();
    }
}
