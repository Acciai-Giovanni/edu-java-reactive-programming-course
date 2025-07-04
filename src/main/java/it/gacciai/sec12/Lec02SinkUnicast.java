package it.gacciai.sec12;


import it.gacciai.common.Util;
import reactor.core.publisher.Sinks;

/*
    We can emit multiple messages. but there will be only one subscriber.
 */
public class Lec02SinkUnicast {

    public static void main(String[] args) {

        demo1();

    }

    private static void demo1(){
        // handle through which we would push items
        // onBackPressureBuffer - unbounded queue
        var sink = Sinks.many().unicast().onBackpressureBuffer();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");
        sink.tryEmitComplete();

        flux.subscribe(Util.subscriber("Giovanni"));
    }

    // we can not have multiple subscribers
    private static void demo2(){
        // handle through which we would push items
        // onBackPressureBuffer - unbounded queue
        var sink = Sinks.many().unicast().onBackpressureBuffer();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

//        sink.tryEmitNext("hi");
//        sink.tryEmitNext("how are you");
//        sink.tryEmitNext("?");

        flux.subscribe(Util.subscriber("Giovanni"));
        flux.subscribe(Util.subscriber("Luca"));
    }

}

