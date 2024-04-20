package it1.aop.homework.dto;

import java.time.Instant;

public interface MethodStatistic {
    String getMethodName();

    Double getSpeedExecution();

    Instant getTime();
}
