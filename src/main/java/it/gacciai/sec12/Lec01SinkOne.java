package it.gacciai.sec12;

import it.gacciai.common.Util;
import it.gacciai.sec04.Lec01FluxCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

public class Lec01SinkOne {

    private static final Logger log = LoggerFactory.getLogger(Lec01FluxCreate.class);

    public static void main(String[] args) {

        demo3();

    }

    //Single subscriber
    private static void demo1(){
        var sink = Sinks.one();
        sink.asMono()
                //.log()
                .subscribe(Util.subscriber());

        sink.tryEmitValue("pippo");
        //sink.tryEmitEmpty();
        //sink.tryEmitError(new RuntimeException("rotto"));
    }

    //Multiple subscribers
    private static void demo2(){
        var sink = Sinks.one();
        var mono = sink.asMono();

        sink.tryEmitValue("pippo");

        mono.subscribe(Util.subscriber("giovanni"));
        mono.subscribe(Util.subscriber("luca"));

        sink.asMono().subscribe(Util.subscriber("marco"));
        sink.asMono().subscribe(Util.subscriber("simone"));

    }

    //Emit Failure Handling
    private static void demo3(){
        var sink = Sinks.one();
        var mono = sink.asMono();

        mono.subscribe(Util.subscriber("giovanni"));

        sink.emitValue("value", ((signalType, emitResult) -> {
            log.info(signalType.name());
            log.info(emitResult.name());
            return false;
        }));

        sink.emitValue("two", (signalType, emitResult) -> {
            log.info(signalType.name());
            log.info(emitResult.name());
            return false; // Retry on true!
        });

    }
}
