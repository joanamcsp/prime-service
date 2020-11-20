package org.joanamcsp.primes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class PrimeServiceImplTest {
    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    private ManagedChannel inProcessChannel;
    private String serverName;

    @Before
    public void setUp() throws Exception {
        serverName = InProcessServerBuilder.generateName();
        inProcessChannel = InProcessChannelBuilder.forName(serverName).directExecutor().build();
        grpcCleanup.register(InProcessServerBuilder.forName(serverName).directExecutor().addService(new PrimeServiceImpl()).build().start());
    }

    @Test
    public void greeterImpl_given_bound_returns_list_of_prime_numbers_less_than_or_equal_to_bound() {
        final List<Integer> primeList = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(1);
        PrimeServiceGrpc.PrimeServiceStub stub = PrimeServiceGrpc.newStub(grpcCleanup
                .register(inProcessChannel));

        stub.getPrimes(
                GetPrimesRequest.newBuilder().setBound(10).build(),
                new StreamObserver<>() {
                    @Override
                    public void onNext(GetPrimesResponse getPrimesResponse) {
                        primeList.add(getPrimesResponse.getPrime());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        fail();
                    }

                    @Override
                    public void onCompleted() {
                        latch.countDown();
                    }
                });

        assertEquals(List.of(2, 3, 5, 7), primeList);
    }

    @Test
    public void greeterImpl_given_bound_that_is_less_than_or_equal_to_2_throws_StatusRuntimeException() {
        StreamObserver<GetPrimesResponse> responseObserver = (StreamObserver<GetPrimesResponse>) mock(StreamObserver.class);
        PrimeServiceGrpc.PrimeServiceStub stub = PrimeServiceGrpc.newStub(grpcCleanup.register(inProcessChannel));

         stub.getPrimes(GetPrimesRequest.newBuilder().setBound(-10).build(), responseObserver);

        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        assertTrue(errorCaptor.getValue() instanceof StatusRuntimeException);
        assertEquals(Status.INVALID_ARGUMENT.getCode(), Status.fromThrowable(errorCaptor.getValue()).getCode());
        assertEquals("Bound cannot be less than 2", Status.fromThrowable(errorCaptor.getValue()).getDescription());
    }
}
