package it.gacciai.sec07;

import it.gacciai.common.Util;
import it.gacciai.sec07.client.ExternalServiceClient;

/*
    Ensure that the external service is up and running!
 */
public class Lec06EventLoopIssueFix {

    public static void main(String[] args) {

        var client = new ExternalServiceClient();

        for (int i = 1; i <= 5; i++) {
            client.getProductName(i)
                    .map(Lec06EventLoopIssueFix::process)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(6);

    }

    private static String process(String input){
        Util.sleepSeconds(1);
        return input + "-processed";
    }

}
