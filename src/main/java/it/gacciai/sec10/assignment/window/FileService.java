package it.gacciai.sec10.assignment.window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class FileService {

    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    private static final String userDirectory = FileSystems.getDefault()
            .getPath("")
            .toAbsolutePath()
            .toString();

    private static final String assetDirectory = userDirectory + FileSystems.getDefault().getSeparator() +
            "target"+ FileSystems.getDefault().getSeparator() +
            "classes"+ FileSystems.getDefault().getSeparator() +
            "sec10"+ FileSystems.getDefault().getSeparator();

    private Path path;

    public FileService(Path path) {
        this.path = path;
    }

    public static FileService openInstance() throws Exception {
        Date date = new Date(System.currentTimeMillis());

        String fileName = date.getTime() + ".txt";

        log.info("Writing file: " + fileName);

        Path dirPath = Paths.get(assetDirectory);

        if (!Files.isDirectory(dirPath)) {
            Files.createDirectory(dirPath);
        }

        Path filePath = dirPath.resolve(fileName);

        if (Files.exists(filePath)) {
            throw new Exception("fileName already exist: " + fileName);
        }

        Files.createFile(filePath);

        return new FileService(filePath);
    }

    public void write(String content) {
        try {
            Files.write(this.path, content.getBytes(), java.nio.file.StandardOpenOption.APPEND);
            Files.write(this.path, "\n".getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Error writing file", e);
        }
    }

    public void writeln() {
        try {
            Files.write(this.path, "\n".getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Error writing file", e);
        }
    }

    public static Mono<Void> writeEvents(Flux<String> flux) {
        try {
            var fileService = FileService.openInstance();

            return flux.doOnNext(e -> fileService.write("*"))
                    .doOnComplete(fileService::writeln)
                    .then();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
