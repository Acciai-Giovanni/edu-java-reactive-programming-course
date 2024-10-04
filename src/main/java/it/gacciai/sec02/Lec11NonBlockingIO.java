package it.gacciai.sec02;


import it.gacciai.common.Util;
import it.gacciai.sec02.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    To demo non-blocking IO
    Ensure that the external service is up and running!
 */
public class Lec11NonBlockingIO {

    private static final Logger log = LoggerFactory.getLogger(Lec11NonBlockingIO.class);

    public static void main(String[] args) {
        log.info("method main starts");

        var client = new ExternalServiceClient();

        for (int i = 0; i < 100; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        log.info("wating for async completion");
        Util.sleepSeconds(3);

        log.info("method main ends");
    }

}
