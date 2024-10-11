package it.gacciai.sec05.assignment;

import reactor.core.publisher.Flux;


public interface ProductService {
    Flux<String> getProductNames();

}
