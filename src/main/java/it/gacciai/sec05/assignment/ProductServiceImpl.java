package it.gacciai.sec05.assignment;

import org.apache.commons.lang3.NotImplementedException;
import reactor.core.publisher.Flux;

public class ProductServiceImpl implements ProductService {

    @Override
    public Flux<String> getProductNames() {
        return Flux.error(new NotImplementedException("Not implemented"));
    }
}
