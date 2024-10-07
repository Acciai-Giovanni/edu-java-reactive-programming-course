package it.gacciai.sec04;

import it.gacciai.common.Util;
import it.gacciai.sec04.assignment.FileReaderServiceImpl;

import java.nio.file.Path;

public class Lec09Assignment {

    public static void main(String[] args) {

        var path = Path.of("src/main/resources/sec04/file.txt");

        var service = new FileReaderServiceImpl();
        service.read(path)
                .subscribe(Util.subscriber())
        ;
    }

}
