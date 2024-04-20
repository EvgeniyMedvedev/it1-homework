package it1.aop.homework.services;

import it1.aop.homework.annotanions.TrackAsyncTime;
import it1.aop.homework.annotanions.TrackTime;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
public class ActionSimulator {

    @TrackTime
    public void anyDoing() {
        RestClient httpClient = RestClient.create();
        Map<?,?> ipify = httpClient.get().uri("https://api.ipify.org/?format=json").retrieve().body(Map.class);
        String ip = (String) Objects.requireNonNull(ipify).get("ip");
        Map<?,?> ipinfo = httpClient.get().uri(String.format("https://ipinfo.io/%s/geo", ip)).retrieve().body(Map.class);

        String city = (String) Objects.requireNonNull(ipinfo).get("city");
        System.out.printf("You're in %s \n", city);
    }

    @TrackAsyncTime
    public void anyAsyncDoing() {
        RestClient httpClient = RestClient.create();
        var resp = httpClient.get().uri("https://api.ipify.org/?format=json").retrieve();
        Map<?,?> ipify = resp.body(Map.class);
        String ip = (String) Objects.requireNonNull(ipify).get("ip");
        Map<?,?> ipinfo = httpClient.get().uri(String.format("https://ipinfo.io/%s/geo", ip)).retrieve().body(Map.class);

        String city = (String) Objects.requireNonNull(ipinfo).get("city");
        System.out.printf("You're in %s \n", city);
    }

    @SneakyThrows
    @TrackAsyncTime
    public void doRandom() {
        Thread.sleep(new Random().nextInt(30) * 100);
    }

}
