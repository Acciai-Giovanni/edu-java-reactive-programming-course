package it.gacciai.sec11.client;

// just for demo
public class ClientError extends RuntimeException {

    public ClientError() {
        super("bad request");
    }

}