package it.gacciai.sec03;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec04FluxFromStream {

    public static void main(String[] args) {
        var list = List.of(1, 2, 3);
        //var stream = list.stream();

        var flux = Flux.fromStream(list::stream);

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
        flux.subscribe(Util.subscriber("sub3"));
    }

}
