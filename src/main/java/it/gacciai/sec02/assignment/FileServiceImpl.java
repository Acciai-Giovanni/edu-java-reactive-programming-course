package it.gacciai.sec02.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.nio.file.*;

public class FileServiceImpl implements FileService{

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    private final String userDirectory = FileSystems.getDefault()
            .getPath("")
            .toAbsolutePath()
            .toString();

    private final String assetDirectory = userDirectory + FileSystems.getDefault().getSeparator() +
            "target"+ FileSystems.getDefault().getSeparator() +
            "classes"+ FileSystems.getDefault().getSeparator() +
            "sec02"+ FileSystems.getDefault().getSeparator();

    @Override
    public Mono<String> read(String fileName) {
        return Mono.fromCallable(() -> read_callable(fileName));
    }

    @Override
    public Mono<Void> write(String fileName, String content) {
        return Mono.fromCallable(() -> write_callable(fileName, content));
    }

    @Override
    public Mono<Void> delete(String fileName) {
        return  Mono.fromCallable(() -> delete_callable(fileName));
    }


    private String read_callable(String fileName) throws Exception {

        log.info("Reading file: " + fileName);

        Path dirPath = Paths.get(assetDirectory);

        if (!Files.isDirectory(dirPath)) {
            throw new Exception("Asset Directory not found");
        }

        Path filePath = dirPath.resolve(fileName);

        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("fileName not found: " + fileName);
        }

        if (!Files.isRegularFile(filePath)) {
            throw new IllegalArgumentException("fileName is not a regular file: " + fileName);
        }

        if (!Files.isReadable(filePath)) {
            throw new IllegalArgumentException("fileName is not readable: " + fileName);
        }

        return Files.readString(filePath);
    }

    private Void write_callable(String fileName, String content) throws Exception {

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

        Files.write(filePath, content.getBytes());

        return null;
    }

    private Void delete_callable(String fileName) throws Exception {

        log.info("Deleting file: " + fileName);

        Path dirPath = Paths.get(assetDirectory);

        if (!Files.isDirectory(dirPath)) {
            throw new Exception("Asset Directory not found");
        }

        Path filePath = dirPath.resolve(fileName);

        if (!Files.exists(filePath)) {
            throw new Exception("fileName does not exist: " + fileName);
        }

        if (!Files.isRegularFile(filePath)) {
            throw new Exception("fileName is not a regular file: " + fileName);
        }

        if (!Files.deleteIfExists(filePath)) {
            throw new Exception("unable to delete fileName");
        }

        return null;
    }
}
