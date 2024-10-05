package it.gacciai.sec03;

import it.gacciai.common.Util;
import it.gacciai.sec03.assignment.StockSubscriber;
import it.gacciai.sec03.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec12Assignment {

    public static final Logger log = LoggerFactory.getLogger(Lec12Assignment.class);

    public static void main(String[] args) {

        var client = new ExternalServiceClient();
        client.getStock()
                .log()
                .map(Long::parseLong)
                .subscribe(new StockSubscriber<>(1000,90,110));

        Util.sleepSeconds(25);

    }

}
