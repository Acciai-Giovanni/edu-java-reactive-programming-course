package it.gacciai.sec06.assignment;

import it.gacciai.common.AbstractHttpClient;
import reactor.core.publisher.Flux;

public class ExternalServiceClient extends AbstractHttpClient {

    public record Order(String item, String category, Integer price, Integer quantity) {
    }

    public Flux<Order> getOrders() {
        return Flux.error(new UnsupportedOperationException("Not implemented"));

        //return this.httpClient.get()
        //        .uri("/demo02/name/stream")
        //        .responseContent()
        //        .asString();
    }

}
