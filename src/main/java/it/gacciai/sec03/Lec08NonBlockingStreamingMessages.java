package it.gacciai.sec03;

import it.gacciai.common.Util;
import it.gacciai.sec03.client.ExternalServiceClient;

/*
    To demo non-blocking IO with streaming messages
    Ensure that the external service is up and running!
 */
public class Lec08NonBlockingStreamingMessages {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();
        client.getNames().subscribe(Util.subscriber("sub1"));
        client.getNames().subscribe(Util.subscriber("sub2"));

        Util.sleepSeconds(10);
    }

}
