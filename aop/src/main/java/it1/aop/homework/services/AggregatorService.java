package it1.aop.homework.services;

import it1.aop.homework.dto.MethodStatistic;
import it1.aop.homework.entity.MethodMetric;

public interface AggregatorService {

    MethodStatistic maxExecMethod();

    MethodStatistic minExecMethod();

    MethodStatistic avgExecMethod();

    Iterable<MethodMetric> getAll();

    void add(MethodMetric methodMetric);
    void addRandomMethod();
}
