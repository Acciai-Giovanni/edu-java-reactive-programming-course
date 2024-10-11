package it.gacciai.sec05;

import it.gacciai.common.Util;
import it.gacciai.sec05.assignment.ProductServiceImpl;

public class Lec11Assignment {

    public static void main(String[] args) {


        var service = new ProductServiceImpl();

        service.getProductName(1)
                .subscribe(Util.subscriber("subscriber 1"));

        service.getProductName(2)
                .subscribe(Util.subscriber("subscriber 2"));

        service.getProductName(3)
                .subscribe(Util.subscriber("subscriber 3"));

        service.getProductName(4)
                .subscribe(Util.subscriber("subscriber 4"));


        Util.sleepSeconds(5);

    }

}
