package it.gacciai.sec12;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Lec01SinkOne {

    public static void main(String[] args) {

        demo2();

    }

    //Single subscriber
    private static void demo1(){
        var sink = Sinks.one();
        sink.asMono()
                //.log()
                .subscribe(Util.subscriber());

        //sink.tryEmitValue("pippo");
        //sink.tryEmitEmpty();
        sink.tryEmitError(new RuntimeException("rotto"));
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
}
