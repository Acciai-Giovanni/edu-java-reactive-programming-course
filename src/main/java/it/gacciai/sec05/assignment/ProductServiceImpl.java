package it.gacciai.sec05.assignment;

import it.gacciai.sec05.client.ExternalServiceClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ProductServiceImpl implements ProductService {

    private final ExternalServiceClient client = new ExternalServiceClient();

    @Override
    public Mono<String> getProductName(Integer id) {

        return client.getProduct(id)
                .timeout(Duration.ofSeconds(2), fallbackIfTimeout(id))
                .switchIfEmpty(fallbackIfEmpty(id))
                ;
    }

    private Mono<String> fallbackIfTimeout(Integer id){
        return  client.getFallbackTimeoutProduct(id);
    }

    private Mono<String> fallbackIfEmpty(Integer id){
        return  client.getFallbackEmptyProduct(id);
    }
}
