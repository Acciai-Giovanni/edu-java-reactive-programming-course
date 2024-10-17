package it.gacciai.sec09.assignment;

import it.gacciai.common.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ExternalServiceClient extends AbstractHttpClient {

    private static final Logger log = LoggerFactory.getLogger(it.gacciai.sec07.client.ExternalServiceClient.class);

    public Mono<Product> getProduct(int productId) {
        return Mono.zip(
                        getProductName(productId),
                        getProductReview(productId),
                        getProductPrice(productId))
                .map(tuple -> new Product(tuple.getT1(), tuple.getT2(), tuple.getT3()))
                ;
    }

    public Mono<String> getProductName(int productId) {
        return this.httpClient.get()
                .uri("/demo05/product/" + productId)
                .responseContent()
                .asString()
                .doOnNext(m -> log.info("next: {}", m))
                .next()
                .publishOn(Schedulers.boundedElastic())
                ;
    }

    public Mono<String> getProductReview(int productId) {
        return this.httpClient.get()
                .uri("/demo05/review/" + productId)
                .responseContent()
                .asString()
                .doOnNext(m -> log.info("next: {}", m))
                .next()
                .publishOn(Schedulers.boundedElastic())
                ;
    }

    public Mono<String> getProductPrice(int productId) {
        return this.httpClient.get()
                .uri("/demo05/price/" + productId)
                .responseContent()
                .asString()
                .doOnNext(m -> log.info("next: {}", m))
                .next()
                .publishOn(Schedulers.boundedElastic())
                ;
    }
}