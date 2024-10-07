package it.gacciai.sec04;

import it.gacciai.common.Util;
import it.gacciai.sec04.helper.AssetProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

/*
    FluxSink is thread safe!
    This is just a demo.
    It does NOT mean we should write code like this!
 */
public class Lec03FluxSinkThreadSafety {

    private static final Logger log = LoggerFactory.getLogger(Lec03FluxSinkThreadSafety.class);

    public static void main(String[] args) {
        demo2Safe();
    }

    private static void demo1Unsafe(){
        var list = new ArrayList<Integer>(); //Thread Unsafe

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

        Util.sleepSeconds(5);

        log.info("list size: {}", list.size());
    }

    private static void demo2Safe(){
        var list = new ArrayList<String>();

        var generator = new AssetProducer();
        var flux = Flux.create(generator);
        flux.subscribe(list::add);

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                generator.generate();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

        Util.sleepSeconds(2);

        log.info("list size: {}", list.size());
    }
}
