package it.gacciai.sec04.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileReaderServiceImpl implements FileReaderService{

    private static final Logger log = LoggerFactory.getLogger(FileReaderServiceImpl.class);

    @Override
    public Flux<String> read(Path path) {

        return Flux.generate(
                () -> Files.newBufferedReader(path),
                (bufferedReader, sink) -> {
                    //log.info("reading next line");

                    try {
                        Optional<String> line = Optional.ofNullable(bufferedReader.readLine());
                        if (line.isPresent()) {
                            //log.info(line.get());
                            sink.next(line.get());
                        } else {
                            log.info("EOF");
                            sink.complete();
                        }
                    } catch (IOException e) {
                        log.info("ERROR");
                        sink.error(e);
                    }

                    return bufferedReader;
                },
                bufferedReader -> {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

        );
    }
}
