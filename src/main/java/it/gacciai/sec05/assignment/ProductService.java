package it.gacciai.sec05.assignment;

import reactor.core.publisher.Mono;


public interface ProductService {
    Mono<String> getProductName(Integer id);

}
