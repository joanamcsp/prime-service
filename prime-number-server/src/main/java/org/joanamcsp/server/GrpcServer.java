package org.joanamcsp.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import org.joanamcsp.primes.PrimeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcServer {

    private static Logger logger = LoggerFactory.getLogger(GrpcServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(5000)
                .addService(new PrimeServiceImpl()).build();

        server.start();
        logger.info("gRPC server listening on 5000");
        server.awaitTermination();
    }
}
