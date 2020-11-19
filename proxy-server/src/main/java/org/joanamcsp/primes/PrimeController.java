package org.joanamcsp.primes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/primes")
public class PrimeController {
    Logger logger = LoggerFactory.getLogger(PrimeController.class);

    @Autowired
    private PrimeClient primeClient;
    ObjectMapper mapper = new ObjectMapper();


    @GetMapping("/{number}")
    public ResponseEntity<StreamingResponseBody> getPrimes(@PathVariable int number) {
        StreamingResponseBody stream = out -> {
            try {
                writeToStream(primeClient.getPrimes(number), GetPrimesResponse::getPrime, out);
            } catch (StatusRuntimeException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
        };
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(stream);
    }

    private <T,U> void writeToStream(Iterator<T> items, Function<T,U> itemMapper, OutputStream out) {
        try {
            JsonGenerator jsonGenerator = mapper.getFactory().createGenerator(out);
            jsonGenerator.writeStartArray();
            items.forEachRemaining(item -> {
                try {
                    mapper.writeValue(jsonGenerator, itemMapper.apply(item));
                } catch (IOException e) {
                    logger.error("Error writing response to stream");
                    e.printStackTrace();
                }
            });
            jsonGenerator.writeEndArray();
            jsonGenerator.close();
        } catch (IOException e) {
            logger.error("Error writing response to stream");
            e.printStackTrace();
        }
    }
}

