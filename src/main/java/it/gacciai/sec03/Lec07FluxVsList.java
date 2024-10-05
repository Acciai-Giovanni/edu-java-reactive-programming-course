package it.gacciai.sec03;

import it.gacciai.common.Util;
import it.gacciai.sec03.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec07FluxVsList{

    public static final Logger log = LoggerFactory.getLogger(Lec07FluxVsList.class);

    public static void main(String[] args) {

        log.info("List Start");
        var list = NameGenerator.getNamesList(3); //blocked for X seconds
        log.info("List: {}", list);
        log.info("List End");

        log.info("Flux Start");
        NameGenerator.getNamesFlux(3)
                .log()
                .subscribe(Util.subscriber()) // elaboration start immediately as soon as we receive each item
        ;
        log.info("Flux End");

        //we could also STOP the flux elaboration early

    }

}
