package it1.aop.homework.aspects;

import it1.aop.homework.entity.MethodMetric;
import it1.aop.homework.services.AggregatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;

@Aspect
@Slf4j
@Component
@AllArgsConstructor
public class TrackAsyncTimeAspect {

    private final AggregatorService aggregatorService;

    @Pointcut("@annotation(it1.aop.homework.annotanions.TrackAsyncTime)")
    public void trackAsyncTimePointcut() {}

    @Around(value = "trackAsyncTimePointcut()")
    public void trackAsyncTime(ProceedingJoinPoint pjp) {
        log.info("Start async tracking time");

        CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            try {
                pjp.proceed();
            } catch (Throwable e) {
                log.warn("Somethink went wrong ", e);
            }

            var methodMetric = new MethodMetric();

            var methodName = pjp.getSignature().getName();
            methodMetric.setMethodName(methodName);
            long endTime = System.currentTimeMillis();
            methodMetric.setTime(Instant.now());
            methodMetric.setSpeedExecution((endTime - startTime) * 1.0);
            aggregatorService.add(methodMetric);
            methodMetric.setTime(methodMetric.getTime().plus(1, ChronoUnit.MINUTES));
            aggregatorService.add(methodMetric);

            methodMetric.setTime(methodMetric.getTime().plus(1, ChronoUnit.MINUTES));
            aggregatorService.add(methodMetric);
            log.info("Finish tracking time");
        });
    }
}
