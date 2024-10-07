package it.gacciai.sec04;

import it.gacciai.common.Util;
import it.gacciai.sec04.helper.AssetProducer;
import reactor.core.publisher.Flux;

public class Lec02FluxCreateRefactor {

    public static void main(String[] args) {
        var generator = new AssetProducer();
        var flux = Flux.create(generator);
        flux.subscribe(Util.subscriber());


        for (int i = 0; i < 10; i++) {
            generator.generate();
        }
    }

}
