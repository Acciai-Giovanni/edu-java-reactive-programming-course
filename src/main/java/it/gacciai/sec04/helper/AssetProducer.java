package it.gacciai.sec04.helper;

import it.gacciai.common.Util;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class AssetProducer implements Consumer<FluxSink<String>> {

    private FluxSink<String> fluxSink;

    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        this.fluxSink = stringFluxSink;
    }

    public void generate(){
        this.fluxSink.next(Util.faker().name().firstName());
    }
}
