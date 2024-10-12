package it.gacciai.sec06.assignment;

import it.gacciai.common.AbstractHttpClient;
import reactor.core.publisher.Flux;

public class ExternalServiceClient extends AbstractHttpClient {

    public record Order(String item, String category, Integer price, Integer quantity) {
    }

    public Flux<Order> getOrders() {
        return this.httpClient.get()
                .uri("/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(content -> {
                    var parts = content.split(":");
                    return new Order(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                })
                .log()
                .publish()
                .autoConnect(2);
    }

}
