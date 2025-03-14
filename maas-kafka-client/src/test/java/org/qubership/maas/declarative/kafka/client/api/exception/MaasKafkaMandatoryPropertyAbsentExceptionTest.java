package org.qubership.maas.declarative.kafka.client.api.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaasKafkaMandatoryPropertyAbsentExceptionTest {
    @Test
    void testExceptionMessageFormat() {
        var e = new MaasKafkaMandatoryPropertyAbsentException("oops, %s did %s again","I", "it");
        assertEquals("oops, I did it again", e.getMessage());
    }
}