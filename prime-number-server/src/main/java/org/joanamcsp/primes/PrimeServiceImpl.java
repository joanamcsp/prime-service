package org.joanamcsp.primes;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.stream.IntStream;
import org.joanamcsp.primes.PrimeServiceGrpc.PrimeServiceImplBase;

public class PrimeServiceImpl extends PrimeServiceImplBase {

    @Override
    public void getPrimes(GetPrimesRequest request, StreamObserver<GetPrimesResponse> responseObserver) {
        if(request.getBound() < 2) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Bound cannot be less than 2").asRuntimeException());
        }

       IntStream.rangeClosed(2, request.getBound())
                .filter(PrimeUtils::isPrime)
                .forEach(prime -> responseObserver.onNext(GetPrimesResponse.newBuilder()
                        .setPrime(prime).build()));

        responseObserver.onCompleted();
    }
}
