package it1.aop.homework.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;

@Data
@Entity(name = "methods_metric")
public class MethodMetric {
    @Id
    @Column(name="method_name", length=30, nullable=false)
    private String methodName;

    private Instant time;

    @Column(name="speed_execution", length=30, nullable=false)
    private Double speedExecution;

}
