package it.gacciai.sec03.client;

import it.gacciai.common.AbstractHttpClient;
import reactor.core.publisher.Flux;

public class ExternalServiceClient extends AbstractHttpClient {

    public Flux<String> getNames() {
        return this.httpClient.get()
                .uri("/demo02/name/stream")
                .responseContent()
                .asString();
    }

    public Flux<String> getStock() {
        return this.httpClient.get()
                .uri("/demo02/stock/stream")
                .responseContent()
                .asString();
    }

}
