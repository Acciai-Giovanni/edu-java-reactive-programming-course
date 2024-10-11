package it.gacciai.sec06;

import it.gacciai.common.Util;
import it.gacciai.sec04.helper.AssetProducer;
import reactor.core.publisher.Flux;

public class Lec05FluxCreateIssueFix {
    public static void main(String[] args) {

        var generator = new AssetProducer();
        var flux = Flux.create(generator).share(); //with SHARE both subscribers receive data
        flux.subscribe(Util.subscriber("subscriber1"));
        flux.subscribe(Util.subscriber("subscriber2"));


        for (int i = 0; i < 10; i++) {
            generator.generate();
        }
    }
}
