package it1.aop.homework.annotanions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface TrackAsyncTime {
}
