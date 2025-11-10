package org.apache.commons.lang3;

import org.apache.commons.lang3.exception.CloneFailedException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CloneFailedExceptionCoverageTest {

    @Test
    public void testConstructorsAndCause() {
        CloneFailedException e1 = new CloneFailedException("boom");
        assertNotNull(e1.getMessage());

        Throwable cause = new RuntimeException("cause");
        CloneFailedException e2 = new CloneFailedException("failed", cause);
        assertEquals(cause, e2.getCause());
        assertTrue(e2.getMessage().contains("failed"));
    }
}
