package it.gacciai.sec06;

import it.gacciai.common.Util;
import it.gacciai.sec06.assignment.ExternalServiceClient;
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
        var service = client.getOrders();

        service
                .transform(RevenueService())
                .subscribe(Util.subscriber("CFO"));

        service
                .transform(InventoryService())
                .subscribe(Util.subscriber("WarehouseManager"));

        Util.sleepSeconds(60);

    }

    private static Function<Flux<ExternalServiceClient.Order>, Flux<String>> InventoryService() {
        return orderFlux -> {
            Map<String, Integer> warehouse = new HashMap<>();

            orderFlux
                    .doOnNext(i -> warehouse.put(i.category(), warehouse.getOrDefault(i.category(), 500) - i.quantity()))
                    .doOnComplete(() -> log.info("inventory completed"))
                    .doOnError(err -> log.error("inventory error", err))
                    .subscribe();

            return Flux.interval(Duration.ofSeconds(2))
                    .map(i -> warehouse.entrySet().stream()
                            .map(e -> e.getKey() + " : " + e.getValue())
                            .reduce("", (a, b) -> a + "\n" + b));
        };
    }

    private static Function<Flux<ExternalServiceClient.Order>, Flux<String>> RevenueService() {
        return orderFlux -> {
            Map<String, Integer> revenue = new HashMap<>();

            orderFlux
                    .doOnNext(i -> revenue.put(i.category(), revenue.getOrDefault(i.category(), 0) + i.price() * i.quantity()))
                    .doOnComplete(() -> log.info("revenue completed"))
                    .doOnError(err -> log.error("revenue  error", err))
                    .subscribe();

            return Flux.interval(Duration.ofSeconds(2))
                    .map(i -> revenue.entrySet().stream()
                            .map(e -> e.getKey() + " : " + e.getValue())
                            .reduce("", (a, b) -> a + "\n" + b));
        };
    }

    //Order stream needs min 2 subscribers
    //order-service produces:

    //String Item
    //String Category
    //Integer Price
    //Integer Quantity

    //item:category:price:quantity

    //inventory service -> Keep track of the quantities per category (local memory with MAP) - Start with 500 items per category.
    //                     Emit every 2 seconds the whole warehouse stock levels to the warehouse manager
    //Revenue service -> Emit every 2 seconds the total revenue per category (local memory with MAP) -> to the CFO
}
