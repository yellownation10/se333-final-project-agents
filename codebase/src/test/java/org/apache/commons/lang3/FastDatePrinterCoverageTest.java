package org.apache.commons.lang3;

import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class FastDatePrinterCoverageTest {

    @Test
    public void testTwelveAndTwentyFourHourFormatting() {
        // twelve-hour pattern (hh) and 24-hour (HH) exercise different fields
    FastDateFormat p12 = FastDateFormat.getInstance("hh", TimeZone.getTimeZone("UTC"), Locale.US);
    FastDateFormat p24 = FastDateFormat.getInstance("HH", TimeZone.getTimeZone("UTC"), Locale.US);

        Date d = new Date(0L);
        String s12 = p12.format(d);
        String s24 = p24.format(d);

        assertNotNull(s12);
        assertNotNull(s24);
        // Both should be short numeric strings
        assertTrue(s12.length() > 0);
        assertTrue(s24.length() > 0);
    }
}
