package it.gacciai.sec09.assignment;

import it.gacciai.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<Product> getProduct(int productId) {
        return Mono.zip(
                        getProductName(productId),
                        getProductReview(productId),
                        getProductPrice(productId))
                .map(tuple -> new Product(tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    private Mono<String> getProductName(int productId) {
        return getResource("product", productId);
    }

    private Mono<String> getProductReview(int productId) {
        return getResource("review", productId);
    }

    private Mono<String> getProductPrice(int productId) {
        return getResource("price", productId);
    }

    private Mono<String> getResource(String resource, int productId) {
        return this.httpClient.get()
                .uri("/demo05/" + resource + "/" + productId)
                .responseContent()
                .asString()
                .next()
                ;
    }
}