package it.gacciai.sec09;

import it.gacciai.common.Util;
import it.gacciai.sec09.helper.Kayak;

public class Lec06MergeUseCase {

    public static void main(String[] args) {

        Kayak.getFlights()
                .subscribe(Util.subscriber());


        Util.sleepSeconds(3);


    }

}
