package it.gacciai.sec10;

import it.gacciai.common.Util;
import it.gacciai.sec10.assignment.groupby.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec06GroupbyAssignment {

    private static final Logger log = LoggerFactory.getLogger(Lec06GroupbyAssignment.class);

    public static void main(String[] args) {

        stream()
                .filter(p -> p.category().equals("Kids") || p.category().equals("Automotive"))
                .groupBy(PurchaseOrder::category)
                .flatMap(Lec06GroupbyAssignment::processOrders)
                .log()
                .subscribe();

        Util.sleepSeconds(60);

    }

    private static Flux<PurchaseOrder> stream() {
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> PurchaseOrder.create());
    }

    private static Flux<PurchaseOrder> processOrders(GroupedFlux<String, PurchaseOrder> groupedFlux) {
        log.info("received flux for {}", groupedFlux.key());

        if (groupedFlux.key().equals("Kids")) {
            return kidsOrderProcessor(groupedFlux);
        } else if (groupedFlux.key().equals("Automotive")) {
            return automotiveOrderProcessor(groupedFlux);
        } else {
            throw new RuntimeException("Not supported category");
        }
    }

    private static Flux<PurchaseOrder> kidsOrderProcessor(GroupedFlux<String, PurchaseOrder> groupedFlux) {
        return groupedFlux
                .flatMap(Lec06GroupbyAssignment::addFreeOrder)
                .doOnComplete(() -> log.info("key: {} completed", groupedFlux.key()));
    }

    private static Flux<PurchaseOrder> automotiveOrderProcessor(GroupedFlux<String, PurchaseOrder> groupedFlux) {
        return groupedFlux
                .map(order -> order.increasePrice(100))
                .doOnComplete(() -> log.info("key: {} completed", groupedFlux.key()));
    }

    private static Flux<PurchaseOrder> addFreeOrder(PurchaseOrder originalOrder){
        return Flux.just(originalOrder, originalOrder.getFreeOrder());
    }
}
