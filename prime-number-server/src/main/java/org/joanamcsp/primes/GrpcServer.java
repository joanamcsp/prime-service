package org.joanamcsp.primes;

import java.io.IOException;

public class PrimeServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(5000)
                .addService(new PrimeServiceImpl() {
                }).build();

        server.start();
        System.out.println("Server listening on 5000");
        server.awaitTermination();
    }
}
