package it.gacciai.sec04;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    To create a flux & emit items programmatically
 */
public class Lec01FluxCreate {

    private static final Logger log = LoggerFactory.getLogger(Lec01FluxCreate.class);

    public static void main(String[] args) {
        Flux.create(fluxSink -> {
                    log.info("Inside create method");
                    String country;
                    do {
                        country = Util.faker().country().name();
                        log.info("Emitting: {}", country);
                        fluxSink.next(country);
                    } while (!country.equalsIgnoreCase("canada"));

                    fluxSink.complete();
                })
                .subscribe(Util.subscriber())
                ;
    }

}
