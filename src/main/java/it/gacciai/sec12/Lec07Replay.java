package it.gacciai.sec12;

import it.gacciai.common.Util;
import reactor.core.publisher.Sinks;

public class Lec07Replay {

    public static void main(String[] args) {

        demo1();

    }


    private static void demo1(){

        // handle through which we would push items
        var sink = Sinks.many().replay().all(); // UNBOUNDED!

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("jake"));
        sink.tryEmitNext("new message");

    }


}
