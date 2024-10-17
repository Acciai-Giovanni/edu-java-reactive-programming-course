package it.gacciai.sec09;

import it.gacciai.common.Util;
import it.gacciai.sec09.assignment.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    Ensure that the external service is up and running!
 */
public class Lec12FluxFlatMapAssignment {

    private static final Logger log = LoggerFactory.getLogger(Lec08ZipAssignment.class);

    public static void main(String[] args) {
        log.info("Start");


        Flux.range(1,10)
                .flatMap(i -> new ExternalServiceClient().getProduct(i), 10)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

}
