package it.gacciai.sec04.assignment;

import org.apache.commons.lang3.NotImplementedException;
import reactor.core.publisher.Flux;

import java.nio.file.Path;

public class FileReaderServiceImpl implements FileReaderService{


    @Override
    public Flux<String> read(Path path) {
        throw new NotImplementedException("Not implemented yet");
        //return null;
    }
}
