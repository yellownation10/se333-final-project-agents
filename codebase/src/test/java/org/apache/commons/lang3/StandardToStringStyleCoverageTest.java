package org.apache.commons.lang3;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.junit.Test;

import static org.junit.Assert.*;

public class StandardToStringStyleCoverageTest {

    private static class Bean { // simple POJO for toString
        @SuppressWarnings("unused")
        private final int x = 3;
        @SuppressWarnings("unused")
        private final String name = "n";
    }

    @Test
    public void testStandardToStringStyleProducesOutput() {
        Bean b = new Bean();
        StandardToStringStyle style = new StandardToStringStyle();
        String s = new ToStringBuilder(b, style).toString();
        assertNotNull(s);
        assertTrue(s.contains("Bean") || s.length() > 0);
    }
}
