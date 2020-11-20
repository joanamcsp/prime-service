package org.joanamcsp.primes;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PrimeClient {
    Logger logger = LoggerFactory.getLogger(PrimeClient.class);
    private PrimeServiceGrpc.PrimeServiceBlockingStub stub;
    private ManagedChannel channel;

    public PrimeClient() {
        channel = ManagedChannelBuilder
                .forAddress("prime-number-server", 5000)
                .usePlaintext()
                .build();
        stub = PrimeServiceGrpc.newBlockingStub(channel);
    }

    public Iterator<GetPrimesResponse> getPrimes(int bound) {
        return stub.getPrimes(GetPrimesRequest.newBuilder().setBound(bound).build());
    }

    public void shutdown() {
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Failure when shutting down Channel");
            e.printStackTrace();
        }
    }
}
