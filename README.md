# prime-service
Simple service to generate a list of prime numbers up to a given number.

## Desing Considerations  
The prime-service setup consists of the prime-number-server and proxy-server. prime-number-service
is a gRPC server that generates a stream of prime numbers. The proxy-server calls the gRPC server and 
then streams the prime numbers through an exposed REST enpoint upon request.
In the first iteration of prime-number-service, the list of primes was being generated on demand
for every request, which can quickly become inefficient for larger limits. For this reason, 
I attempted a first optimization approach through the generation of a large list of primes at startup.
This is done by preprocessing a BitSet (to reduce memory consumption) that will yield all 
primes up to a limit of `100_000`, using the Sieve of Eratosthenes algorithm.
This limit can be increased further, but timeouts can still happen when processing a request,so further
optimizations can be made.

## Set up an running  
You need to install java 11, maven, docker and docker-compose. Then run the setup with the following 
commands:  
`mvn clean package`  
`docker-compose build`  
`docker-compose up`  

This will package the applications and start docker containers for prime-number-server and proxy-server.
You can then interact with the proxy-service at the exposed endpoint:  
`GET localhost:8080/primes/<number>`

## Unit tests  
For unit tests run
`mvn clean test`

## Component tests  
To run the integration tests for prime-service, you can start the docker setup as specified above
and then run `mvn clean verify`.









