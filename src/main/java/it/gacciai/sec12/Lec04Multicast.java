package it.gacciai.sec12;

import it.gacciai.common.Util;
import reactor.core.publisher.Sinks;

public class Lec04Multicast {

    public static void main(String[] args) {

        demo2();

    }


    private static void demo1(){

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        flux.subscribe(Util.subscriber("jake"));
        sink.tryEmitNext("new message");

    }

    // warmup
    private static void demo2(){

        System.setProperty("reactor.bufferSize.small", "32");

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer(); // BOUNDED!!! scarta gli elementi che sforano la queue

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        for (int i = 0; i < 40; i++) {
            sink.tryEmitNext("Flooding with element: "+ i);
        }

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));
        flux.subscribe(Util.subscriber("jake"));

        sink.tryEmitNext("new message");

    }

}
