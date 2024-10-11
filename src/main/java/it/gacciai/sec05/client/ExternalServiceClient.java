package it.gacciai.sec05.client;

import it.gacciai.common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<String> getProduct(Integer id) {

        return Mono.from(this.httpClient.get()
                .uri("/demo03/product/" + id)
                .responseContent()
                .asString());
    }

    public Mono<String> getFallbackEmptyProduct(Integer id) {
        return Mono.from(this.httpClient.get()
                .uri("/demo03/empty-fallback/product/" + id)
                .responseContent()
                .asString());
    }

    public Mono<String> getFallbackTimeoutProduct(Integer id) {
        return Mono.from(
                this.httpClient.get()
                .uri("/demo03/timeout-fallback/product/" + id)
                .responseContent()
                .asString());
    }

}
