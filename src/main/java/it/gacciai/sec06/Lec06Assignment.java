package it.gacciai.sec06;

import it.gacciai.common.Util;
import it.gacciai.sec06.assignment.ExternalServiceClient;
import it.gacciai.sec06.assignment.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Lec06Assignment {

    private static final Logger log = LoggerFactory.getLogger(Lec06Assignment.class);

    public static void main(String[] args) {

        var client = new ExternalServiceClient();

        client.getOrders()
                .transform(revenueTransformerService())
                .subscribe(Util.subscriber("CFO"));

        client.getOrders()
                .transform(inventoryTransformerService())
                .subscribe(Util.subscriber("WarehouseManager"));

        Util.sleepSeconds(60);

    }

    private static Function<Flux<Order>, Flux<String>> inventoryTransformerService() {
        return orderFlux -> {
            Map<String, Integer> warehouse = new HashMap<>();

            orderFlux
                    .doOnNext(i -> warehouse.put(i.category(), warehouse.getOrDefault(i.category(), 500) - i.quantity()))
                    .doOnComplete(() -> log.info("inventory completed"))
                    .doOnError(err -> log.error("inventory error", err))
                    .subscribe();

            return Flux.interval(Duration.ofSeconds(2))
                    .map(i -> warehouse.toString());
        };
    }

    private static Function<Flux<Order>, Flux<String>> revenueTransformerService() {
        return orderFlux -> {
            Map<String, Integer> revenue = new HashMap<>();

            orderFlux
                    .doOnNext(i -> revenue.put(i.category(), revenue.getOrDefault(i.category(), 0) + i.price() * i.quantity()))
                    .doOnComplete(() -> log.info("revenue completed"))
                    .doOnError(err -> log.error("revenue  error", err))
                    .subscribe();

            return Flux.interval(Duration.ofSeconds(2))
                    .map(i -> revenue.toString());
        };
    }
}
