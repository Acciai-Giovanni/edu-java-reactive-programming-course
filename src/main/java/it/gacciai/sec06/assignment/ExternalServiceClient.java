package it.gacciai.sec06.assignment;

import it.gacciai.common.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Objects;

public class ExternalServiceClient extends AbstractHttpClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);

    private Flux<Order> orderFlux;

    public Flux<Order> getOrders(){
        if(Objects.isNull(orderFlux)){
            this.orderFlux = orderStream();
        }
        return this.orderFlux;
    }

    private Flux<Order> orderStream() {
        return this.httpClient.get()
                .uri("/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(content -> {
                    var parts = content.split(":");
                    return new Order(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                })
                .doOnNext(order -> log.info("{}}", order))
                .publish()
                .refCount(2);
    }

}
