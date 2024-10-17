package it.gacciai.sec09;

import it.gacciai.common.Util;
import it.gacciai.sec09.assignment.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Lec08ZipAssignment {

    private static final Logger log = LoggerFactory.getLogger(Lec08ZipAssignment.class);

    public static void main(String[] args) {
        log.info("Start");

        var client = new ExternalServiceClient(); //Mono<Product>

        for (int i = 1; i < 10; i++) {
            client.getProduct(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(5);
    }

}
