package it1.aop.homework.repo;

import it1.aop.homework.dto.MethodStatistic;
import it1.aop.homework.entity.MethodMetric;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodMetricRepo extends CrudRepository<MethodMetric, String> {

    MethodStatistic findTopByOrderByMethodNameDesc();

    MethodStatistic findTopByOrderByMethodNameAsc();

    @Query(value = "SELECT method_name, AVG(speed_execution) as speed_execution, time_bucket('1 minutes', time) from methods_metric GROUP BY method_name,time", nativeQuery = true)
    MethodStatistic getAvgExecutionTimeMethod();
}
